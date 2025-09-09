package edu.overdrive.relaciones_room.relaciones.one_to_one;

import androidx.room.Embedded;
import androidx.room.Relation;

import edu.overdrive.relaciones_room.entidades.Perfil;
import edu.overdrive.relaciones_room.entidades.Usuario;

//No se marca como entidad
public class RelacionPerfilUsuario {
    @Embedded
    public Perfil perfil;

    @Relation(
            parentColumn = "id_perfil",
            entityColumn = "id_perfil_fk"
    )
    public Usuario usuario;
}
