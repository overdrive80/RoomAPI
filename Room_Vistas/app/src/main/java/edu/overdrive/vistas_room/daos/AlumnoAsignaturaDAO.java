package edu.overdrive.vistas_room.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

import edu.overdrive.vistas_room.entidades.Alumno_Asignatura;

@Dao
public interface AlumnoAsignaturaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertarTodos(List<Alumno_Asignatura> alumnoAsignatura);
}
