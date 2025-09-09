package edu.overdrive.vistas_room.vistas;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

@DatabaseView(
        value = "SELECT " +
                "asig.id_asignatura, " +
                "asig.nombre, " +
                "asig.curso, " +
                "COUNT(aa.idAlumno) AS total_alumnos " +
                "FROM asignaturas asig " +
                "LEFT JOIN alumnos_asignaturas aa ON asig.id_asignatura = aa.idAsignatura " +
                "GROUP BY asig.id_asignatura",
        viewName = "vista_asignaturas_conteo"
)
public class VistaAsignaturaConteo {
    @ColumnInfo(name = "id_asignatura")
    public Long idAsignatura;

    @ColumnInfo(name = "nombre")
    public String nombre;

    @ColumnInfo(name = "curso")
    public String curso;

    @ColumnInfo(name = "total_alumnos")
    public int totalAlumnos;
}
