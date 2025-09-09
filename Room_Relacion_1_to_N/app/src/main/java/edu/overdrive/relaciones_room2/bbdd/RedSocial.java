package edu.overdrive.relaciones_room2.bbdd;

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

import edu.overdrive.relaciones_room2.daos.EmailDAO;
import edu.overdrive.relaciones_room2.daos.UsuarioDAO;
import edu.overdrive.relaciones_room2.entidades.Email;
import edu.overdrive.relaciones_room2.entidades.Usuario;

@Database(
        entities = {Usuario.class, Email.class},
        version = 1,
        exportSchema = false)
public abstract class RedSocial extends RoomDatabase {
    private static final String TAG = "clientes";

    // Exposición de DAOs: estos métodos los autogenera Room en una clase que implementa la interface
    public abstract UsuarioDAO usuarioDAO();

    public abstract EmailDAO emailDAO();

    private static final String BASEDATOS = "clientes-db";

    //Implementar un patrón Singleton con mulithilo
    private static volatile RedSocial INSTANCIA;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService servicioExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Si hacemos al metodo synchronized: solo crea 1 instancia de la BD
    public static RedSocial getInstance(final Context context) {

        if (INSTANCIA == null) {
            synchronized (RedSocial.class) {
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

    private static RedSocial crearInstancia(Context context) {
        Log.d(TAG, "Creando instancia de la base de datos");
        return Room.databaseBuilder(context, RedSocial.class, BASEDATOS)
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

                poblarBaseDatos(RedSocial.INSTANCIA);
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d(TAG, "Base de datos abierta");
        }
    };

    private static void poblarBaseDatos(RedSocial database) {
        if (database == null) return;

        try {
            // Segundo entidades debiles. Clases con claves foraneas declaradas.
            List<Usuario> usuarios = Arrays.asList(
                    new Usuario(1L, "Usuario #1"),
                    new Usuario(2L, "Usuario #2")
            );

            // Primero entidades fuertes.
            List<Email> emails = Arrays.asList(
                    new Email(1L, "email #1", 1L),
                    new Email(2L, "email #2", 1L),
                    new Email(3L, "email #3", 1L),
                    new Email(4L, "email #1", 2L),
                    new Email(5L, "email #2", 2L)
            );

            //Ejecutar como una unica transacción a nivel de POO
            database.runInTransaction(new Runnable() {
                @Override
                public void run() {
                    // Primero entidades fuertes.
                    database.usuarioDAO().insertarTodos(usuarios);
                    // Segundo entidades debiles. Clases con claves foraneas declaradas.
                    database.emailDAO().insertarTodos(emails);
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