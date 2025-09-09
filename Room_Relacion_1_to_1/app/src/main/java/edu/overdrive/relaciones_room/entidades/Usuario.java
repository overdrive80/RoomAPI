package edu.overdrive.relaciones_room.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuario",
        foreignKeys = {@ForeignKey(
                entity = Perfil.class,
                parentColumns = "id_perfil",
                childColumns = "id_perfil_fk",
                onDelete = ForeignKey.CASCADE
        )}
)
public class Usuario {
    @PrimaryKey
    @ColumnInfo(name = "id_usuario")
    public Long idUsuario;

    public String nombre;

    @ColumnInfo(name = "id_perfil_fk", index = true)
    public Long idPerfil;  // FK

    @Ignore
    public Usuario(){}

    public Usuario(Long idUsuario, String nombre, Long idPerfil) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.idPerfil = idPerfil;
    }
}