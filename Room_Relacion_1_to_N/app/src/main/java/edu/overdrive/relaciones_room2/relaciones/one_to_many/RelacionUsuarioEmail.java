package edu.overdrive.relaciones_room2.relaciones.one_to_many;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import edu.overdrive.relaciones_room2.entidades.Email;
import edu.overdrive.relaciones_room2.entidades.Usuario;

//No se marca como entidad
public class RelacionUsuarioEmail {
    @Embedded
    public Usuario usuario;

    @Relation(
            parentColumn = "id_usuario",
            entityColumn = "id_usuario_fk"
    )

    //Aqui se diferencia con la relacion uno a uno
    //Se declara una lista
    public List<Email> listaEmails;
}
