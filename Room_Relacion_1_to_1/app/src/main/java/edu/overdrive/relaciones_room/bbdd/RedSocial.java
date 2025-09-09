package edu.overdrive.relaciones_room.bbdd;

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

import edu.overdrive.relaciones_room.daos.PerfilDAO;
import edu.overdrive.relaciones_room.daos.UsuarioDAO;
import edu.overdrive.relaciones_room.entidades.Perfil;
import edu.overdrive.relaciones_room.entidades.Usuario;

@Database(
        entities = {Usuario.class, Perfil.class},
        version = 1,
        exportSchema = false)
public abstract class RedSocial extends RoomDatabase {
    private static final String TAG = "redsocial";

    // Exposición de DAOs: estos métodos los autogenera Room en una clase que implementa la interface
    public abstract UsuarioDAO usuarioDAO();
    public abstract PerfilDAO perfilDAO();

    private static final String BASEDATOS = "redsocial-db";

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
            // Primero entidades fuertes.
            List<Perfil> perfiles = Arrays.asList(
                    new Perfil(1L, "bio #1", "url:www.site1.es"),
                    new Perfil(2L, "bio #2", "url:www.site2.es"),
                    new Perfil(3L, "bio #3", "url:www.site3.es"),
                    new Perfil(4L, "bio #4", "url:www.site4.es"),
                    new Perfil(5L, "bio #5", "url:www.site5.es")
            );

            // Segundo entidades debiles. Clases con claves foraneas declaradas.
            List<Usuario> usuarios = Arrays.asList(
                    new Usuario(1L, "Usuario #1", 1L),
                    new Usuario(2L, "Usuario #2", 2L),
                    new Usuario(3L, "Usuario #3", 3L),
                    new Usuario(4L, "Usuario #4", 4L),
                    new Usuario(5L, "Usuario #5", 5L)
            );

            //Ejecutar como una unica transacción a nivel de POO
            database.runInTransaction(new Runnable() {
                @Override
                public void run() {
                    // Primero entidades fuertes.
                    database.perfilDAO().insertarTodos(perfiles);
                    // Segundo entidades debiles. Clases con claves foraneas declaradas.
                    database.usuarioDAO().insertarTodos(usuarios);
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