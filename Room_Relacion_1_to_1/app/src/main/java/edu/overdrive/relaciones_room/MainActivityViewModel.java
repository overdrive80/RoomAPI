package edu.overdrive.relaciones_room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

import edu.overdrive.relaciones_room.relaciones.one_to_one.RelacionPerfilUsuario;
import edu.overdrive.relaciones_room.repos.RedSocialRepositorio;

public class MainActivityViewModel extends AndroidViewModel {

    private RedSocialRepositorio repo;
    private LiveData<List<RelacionPerfilUsuario>> listaPerfiles = new MutableLiveData<>(new ArrayList<>());

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repo = new RedSocialRepositorio(application);

        listaPerfiles = repo.getPerfilesConUsuarios();
    }

    public LiveData<List<RelacionPerfilUsuario>> getPerfilesConUsuarios() {
        return listaPerfiles;
    }

}