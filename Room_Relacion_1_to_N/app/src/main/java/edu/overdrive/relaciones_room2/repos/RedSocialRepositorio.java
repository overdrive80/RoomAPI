package edu.overdrive.relaciones_room2.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.overdrive.relaciones_room2.bbdd.RedSocial;
import edu.overdrive.relaciones_room2.daos.EmailDAO;
import edu.overdrive.relaciones_room2.daos.UsuarioDAO;
import edu.overdrive.relaciones_room2.relaciones.one_to_many.RelacionUsuarioEmail;

/**
 * En el repositorio no deben vivir objetos LiveData
 */
public class RedSocialRepositorio {
    private static final String TAG = RedSocialRepositorio.class.getSimpleName();
    public RedSocial basedatos;
    private UsuarioDAO usuarioDAO;
    private EmailDAO emailDAO;

    public RedSocialRepositorio(Application application) {
        basedatos = RedSocial.getInstance(application);
        usuarioDAO = basedatos.usuarioDAO();
        emailDAO = basedatos.emailDAO();
    }

    public LiveData<List<RelacionUsuarioEmail>> getUsuariosConEmails() {
        return usuarioDAO.getUsuariosConEmails();

    }
}