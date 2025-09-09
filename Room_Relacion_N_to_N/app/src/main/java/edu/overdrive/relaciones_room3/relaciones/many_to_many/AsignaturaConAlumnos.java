package edu.overdrive.relaciones_room3.relaciones.many_to_many;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import edu.overdrive.relaciones_room3.entidades.Alumno;
import edu.overdrive.relaciones_room3.entidades.Alumno_Asignatura;
import edu.overdrive.relaciones_room3.entidades.Asignatura;

// Asignatura --> Alumnos
public class AsignaturaConAlumnos {
    @Embedded
    public Asignatura asignatura;

    @Relation(
            entity = Alumno.class,
            parentColumn = "id_asignatura",
            entityColumn = "id_alumno",
            associateBy = @Junction(
                    value = Alumno_Asignatura.class,
                    parentColumn = "idAsignatura",
                    entityColumn = "idAlumno")
    )
    public List<Alumno> alumnos;
}
