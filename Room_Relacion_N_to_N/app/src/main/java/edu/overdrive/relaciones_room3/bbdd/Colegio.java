package edu.overdrive.relaciones_room3.bbdd;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.overdrive.relaciones_room3.daos.AlumnoAsignaturaDAO;
import edu.overdrive.relaciones_room3.daos.AlumnoDAO;
import edu.overdrive.relaciones_room3.daos.AsignaturaDAO;
import edu.overdrive.relaciones_room3.entidades.Alumno;
import edu.overdrive.relaciones_room3.entidades.Alumno_Asignatura;
import edu.overdrive.relaciones_room3.entidades.Asignatura;

@Database(
        entities = {Alumno.class, Asignatura.class, Alumno_Asignatura.class},
        version = 1,
        exportSchema = false)
public abstract class Colegio extends RoomDatabase {
    private static final String TAG = "colegio";

    // Exposición de DAOs: estos métodos los autogenera Room en una clase que implementa la interface
    public abstract AlumnoDAO alumnoDAO();

    public abstract AsignaturaDAO asignaturaDAO();

    public abstract AlumnoAsignaturaDAO alumnoAsignaturaDAO();

    private static final String BASEDATOS = "colegio-db";

    //Implementar un patrón Singleton con mulithilo
    private static volatile Colegio INSTANCIA;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService servicioExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Si hacemos al metodo synchronized: solo crea 1 instancia de la BD
    public static Colegio getInstance(final Context context) {

        if (INSTANCIA == null) {
            synchronized (Colegio.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = crearInstancia(context);

                    inicializar();
                }
            }
        }

        return INSTANCIA;
    }

    /**
     * Fuerza la creación de la base de datos.
     * La base de datos no se crea hasta que la primera operación de acceso.
     */
    private static void inicializar() {
        //Fuerza la creación de la base de datos
        servicioExecutor.execute(() -> {
            INSTANCIA.getOpenHelper().getWritableDatabase();
        });
    }

    private static Colegio crearInstancia(Context context) {
        Log.d(TAG, "Creando instancia de la base de datos");
        return Room.databaseBuilder(context, Colegio.class, BASEDATOS)
                .allowMainThreadQueries()
                .addCallback(accionesCicloVida)
                .build();
    }

    // Poblar BBDD mediante callback
    static RoomDatabase.Callback accionesCicloVida = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "Base de datos creada, poblando datos...");
            // Ejecutar en el executor para evitar bloquear el hilo principal
            servicioExecutor.execute(() -> {

                poblarBaseDatos(Colegio.INSTANCIA);
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d(TAG, "Base de datos abierta");
        }
    };

    private static void poblarBaseDatos(Colegio database) {
        if (database == null) return;

        try {
            // Primero una entidad
            List<Alumno> alumnos = Arrays.asList(
                    new Alumno(1L, "Alumno #1", "email #1"),
                    new Alumno(2L, "Alumno #2", "email #2")
            );

            // Segundo otra entidad
            List<Asignatura> asignaturas = Arrays.asList(
                    new Asignatura(1L, "asignatura #1", "1"),
                    new Asignatura(2L, "asignatura #2", "1"),
                    new Asignatura(3L, "asignatura #3", "1"),
                    new Asignatura(4L, "asignatura #4", "2"),
                    new Asignatura(5L, "asignatura #5", "2")
            );

            // Tercero la tabla de referencias cruzadas o asociativa
            List<Alumno_Asignatura> alumno_asignaturas = Arrays.asList(
                    new Alumno_Asignatura(1L, 1L),
                    new Alumno_Asignatura(1L, 2L),
                    new Alumno_Asignatura(1L, 3L),
                    new Alumno_Asignatura(2L, 1L),
                    new Alumno_Asignatura(2L, 4L),
                    new Alumno_Asignatura(2L, 5L)
            );

            //Ejecutar como una unica transacción a nivel de POO
            database.runInTransaction(new Runnable() {
                @Override
                public void run() {
                    database.alumnoDAO().insertarTodos(alumnos);
                    database.asignaturaDAO().insertarTodos(asignaturas);

                    //Insertar datos relacionados
                    database.alumnoAsignaturaDAO().insertarTodos(alumno_asignaturas);
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Error al poblar BD: " + e.getMessage());
        }
    }

    public static void anularInstancia() {
        INSTANCIA = null;
    }
}