package edu.overdrive.relaciones_room2.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "usuario")
public class Usuario {
    @PrimaryKey
    @ColumnInfo(name = "id_usuario")
    public Long idUsuario;

    @ColumnInfo(name = "nombre")
    public String nombre;

    public Usuario(Long idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }
}