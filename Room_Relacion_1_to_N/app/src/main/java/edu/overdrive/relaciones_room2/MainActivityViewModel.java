package edu.overdrive.relaciones_room2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import edu.overdrive.relaciones_room2.relaciones.one_to_many.RelacionUsuarioEmail;
import edu.overdrive.relaciones_room2.repos.RedSocialRepositorio;

public class MainActivityViewModel extends AndroidViewModel {

    private RedSocialRepositorio repo;
    private LiveData<List<RelacionUsuarioEmail>> listaUsuariosConEmail = new MutableLiveData<>(new ArrayList<>());

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repo = new RedSocialRepositorio(application);

        listaUsuariosConEmail = repo.getUsuariosConEmails();
    }

    public LiveData<List<RelacionUsuarioEmail>> getUsuariosConEmails() {
        return listaUsuariosConEmail;
    }

}