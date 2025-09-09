package edu.overdrive.relaciones_room3.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "alumnos")
public class Alumno {
    @PrimaryKey
    @ColumnInfo(name = "id_alumno")
    public Long idAlumno;

    @ColumnInfo
    public String nombre;

    @ColumnInfo(name = "correo")
    public String email;

    public Alumno(Long idAlumno, String nombre, String email) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.email = email;
    }
}