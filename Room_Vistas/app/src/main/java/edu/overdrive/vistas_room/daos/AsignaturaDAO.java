package edu.overdrive.vistas_room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import edu.overdrive.vistas_room.entidades.Asignatura;
import edu.overdrive.vistas_room.relaciones.many_to_many.AsignaturaConAlumnos;
import edu.overdrive.vistas_room.vistas.VistaAsignaturaAlumno;
import edu.overdrive.vistas_room.vistas.VistaAsignaturaConteo;

@Dao
public interface AsignaturaDAO {

    // INSERTAR
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(Asignatura asignatura);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertarTodos(List<Asignatura> asignatura);

    // CONSULTAR
    @Query("SELECT * FROM asignaturas WHERE id_asignatura = :id")
    LiveData<Asignatura> getItemPorId(Integer id);

    @Query("SELECT * FROM asignaturas ORDER BY nombre ASC")
    LiveData<List<Asignatura>> getTodosOrdenados();

    // ACTUALIZAR
    @Update
    int actualizar(Asignatura asignatura);

    @Update
    int actualizarTodos(List<Asignatura> asignatura);

    // BORRAR
    @Delete
    int borrar(Asignatura asignatura);

    @Delete
    int borrarTodos(List<Asignatura> asignatura);

    @Query("DELETE FROM asignaturas WHERE id_asignatura = :id")
    int borrarPorId(Long id);

    // CONSULTAR RELACIONES
    @Transaction
    @Query("SELECT * FROM asignaturas")
    public LiveData<List<AsignaturaConAlumnos>> getAsignaturasConAlumnos();

    // CONSULTAR VISTAS
    @Query("SELECT * FROM vista_asignaturas_alumnos WHERE id_asignatura = :idAsignatura")
    public LiveData<List<VistaAsignaturaAlumno>> getVista_AsignaturasConAlumnos(Long idAsignatura);

    @Query("SELECT * FROM vista_asignaturas_conteo ORDER BY total_alumnos DESC")
    public LiveData<List<VistaAsignaturaConteo>> getVista_AsignaturasPorPopularidad();
}
