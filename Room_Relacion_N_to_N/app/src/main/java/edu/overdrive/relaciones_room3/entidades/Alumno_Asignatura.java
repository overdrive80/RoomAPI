package edu.overdrive.relaciones_room3.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "alumnos_asignaturas",
        primaryKeys = {"idAlumno", "idAsignatura"},
        foreignKeys = {
                @ForeignKey(
                        entity = Alumno.class,
                        parentColumns = "id_alumno",
                        childColumns = "idAlumno",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Asignatura.class,
                        parentColumns = "id_asignatura",
                        childColumns = "idAsignatura",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Alumno_Asignatura {
    @NonNull
    @ColumnInfo(index = true)
    public Long idAlumno;

    @NonNull
    @ColumnInfo(index = true)
    public Long idAsignatura;

    public Alumno_Asignatura(Long idAlumno, Long idAsignatura) {
        this.idAlumno = idAlumno;
        this.idAsignatura = idAsignatura;
    }
}
