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

import edu.overdrive.vistas_room.entidades.Alumno;
import edu.overdrive.vistas_room.relaciones.many_to_many.AlumnoConAsignaturas;

@Dao
public interface AlumnoDAO {

    // INSERTAR
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(Alumno alumno);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertarTodos(List<Alumno> alumnos);

    // CONSULTAR
    @Query("SELECT * FROM alumnos WHERE id_alumno = :id")
    LiveData<Alumno> getItemPorId(Integer id);

    @Query("SELECT * FROM alumnos ORDER BY nombre ASC")
    LiveData<List<Alumno>> getTodosOrdenados();

    // ACTUALIZAR
    @Update
    int actualizar(Alumno alumno);

    @Update
    int actualizarTodos(List<Alumno> alumnos);

    // BORRAR
    @Delete
    int borrar(Alumno alumno);

    @Delete
    int borrarTodos(List<Alumno> alumnos);

    @Query("DELETE FROM alumnos WHERE id_alumno = :id")
    int borrarPorId(Integer id);

    // RELACIONES
    // CONSULTAR RELACIONES
    @Transaction
    @Query("SELECT * FROM alumnos")
    public LiveData<List<AlumnoConAsignaturas>> getAlumnosConAsignaturas();

}