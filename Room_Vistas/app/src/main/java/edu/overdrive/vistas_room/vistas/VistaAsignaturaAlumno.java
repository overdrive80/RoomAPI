package edu.overdrive.vistas_room.vistas;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

@DatabaseView(
        value = "SELECT " +
                "asig.id_asignatura, " +
                "asig.nombre AS nombre_asignatura, " +
                "asig.curso, " +
                "a.id_alumno, " +
                "a.nombre AS nombre_alumno, " +
                "a.correo " +
                "FROM asignaturas asig " +
                "INNER JOIN alumnos_asignaturas aa ON asig.id_asignatura = aa.idAsignatura " +
                "INNER JOIN alumnos a ON aa.idAlumno = a.id_alumno",
        viewName = "vista_asignaturas_alumnos"
)
public class VistaAsignaturaAlumno {
    @ColumnInfo(name = "id_asignatura")
    public Long idAsignatura;

    @ColumnInfo(name = "nombre_asignatura")
    public String nombreAsignatura;

    @ColumnInfo(name = "curso")
    public String curso;

    @ColumnInfo(name = "id_alumno")
    public Long idAlumno;

    @ColumnInfo(name = "nombre_alumno")
    public String nombreAlumno;

    @ColumnInfo(name = "correo")
    public String email;
}

