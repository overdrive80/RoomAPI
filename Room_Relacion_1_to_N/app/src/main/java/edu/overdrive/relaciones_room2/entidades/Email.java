package edu.overdrive.relaciones_room2.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "email",
        foreignKeys = {@ForeignKey(
                entity = Usuario.class,
                parentColumns = "id_usuario",
                childColumns = "id_usuario_fk",
                onDelete = ForeignKey.CASCADE
        )}
)
public class Email {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id_email")
    public Long idEmail;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "id_usuario_fk", index = true)
    public Long idUsuario;

    public Email(@NonNull Long idEmail, String email, Long idUsuario) {
        this.idEmail = idEmail;
        this.email = email;
        this.idUsuario = idUsuario;
    }
}




