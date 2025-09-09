package edu.overdrive.relaciones_room3.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import edu.overdrive.relaciones_room3.entidades.Asignatura;
import edu.overdrive.relaciones_room3.relaciones.many_to_many.AsignaturaConAlumnos;

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

}
