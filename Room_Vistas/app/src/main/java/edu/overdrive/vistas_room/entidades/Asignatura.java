package edu.overdrive.vistas_room.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "asignaturas")
public class Asignatura {

    @PrimaryKey
    @ColumnInfo(name = "id_asignatura")
    public Long idAsignatura;

    @ColumnInfo(name = "nombre")
    public String nombre;

    @ColumnInfo(name = "curso")
    public String curso;

    public Asignatura(Long idAsignatura, String nombre, String curso) {
        this.idAsignatura = idAsignatura;
        this.nombre = nombre;
        this.curso = curso;
    }
}




