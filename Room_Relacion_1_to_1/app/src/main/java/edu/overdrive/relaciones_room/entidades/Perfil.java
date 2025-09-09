package edu.overdrive.relaciones_room.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// Esta sería la entidad fuerte
@Entity(tableName = "perfil")
public class Perfil {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id_perfil")
    public Long idPerfil;

    public String bio;
    public String avatarUrl;

    @Ignore
    public Perfil() {     }

    public Perfil(Long idPerfil, String bio, String avatarUrl) {
        this.idPerfil = idPerfil;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }
}




