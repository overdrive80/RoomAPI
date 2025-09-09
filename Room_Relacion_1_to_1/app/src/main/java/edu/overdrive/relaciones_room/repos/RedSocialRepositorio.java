package edu.overdrive.relaciones_room.repos;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.overdrive.relaciones_room.bbdd.RedSocial;
import edu.overdrive.relaciones_room.daos.PerfilDAO;
import edu.overdrive.relaciones_room.daos.UsuarioDAO;
import edu.overdrive.relaciones_room.relaciones.one_to_one.RelacionPerfilUsuario;

/**
 * En el repositorio no deben vivir objetos LiveData
 */
public class RedSocialRepositorio {
    private static final String TAG = RedSocialRepositorio.class.getSimpleName();
    public RedSocial basedatos;
    private UsuarioDAO usuarioDAO;
    private PerfilDAO perfilDAO;

    public RedSocialRepositorio(Application application) {
        basedatos = RedSocial.getInstance(application);
        usuarioDAO = basedatos.usuarioDAO();
        perfilDAO = basedatos.perfilDAO();
    }

    public LiveData<List<RelacionPerfilUsuario>> getPerfilesConUsuarios(){
        return perfilDAO.getPerfilesConUsuarios();

    }
}