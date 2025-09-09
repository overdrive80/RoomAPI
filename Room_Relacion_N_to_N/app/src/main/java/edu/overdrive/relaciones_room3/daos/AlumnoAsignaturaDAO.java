package edu.overdrive.relaciones_room3.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

import edu.overdrive.relaciones_room3.entidades.Alumno_Asignatura;

@Dao
public interface AlumnoAsignaturaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertarTodos(List<Alumno_Asignatura> alumnoAsignatura);
}
