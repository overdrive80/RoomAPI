package edu.overdrive.relaciones_room3.relaciones.many_to_many;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import edu.overdrive.relaciones_room3.entidades.Alumno;
import edu.overdrive.relaciones_room3.entidades.Alumno_Asignatura;
import edu.overdrive.relaciones_room3.entidades.Asignatura;

//No se marca como entidad
// Alumno -> Asignaturas
public class AlumnoConAsignaturas {
    @Embedded
    public Alumno alumno;

    @Relation(
            entity = Asignatura.class,
            parentColumn = "id_alumno",
            entityColumn = "id_asignatura",
            associateBy = @Junction(
                    value = Alumno_Asignatura.class,
                    parentColumn = "idAlumno",
                    entityColumn = "idAsignatura")
    )
    public List<Asignatura> asignaturas;
}
