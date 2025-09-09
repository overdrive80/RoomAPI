package edu.overdrive.vistas_room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import edu.overdrive.vistas_room.repos.ColegioRepositorio;
import edu.overdrive.vistas_room.vistas.VistaAsignaturaAlumno;

public class ActivityVistasViewModel extends AndroidViewModel {
    private ColegioRepositorio repo;
    private MutableLiveData<Long> numeroSeleccion = new MutableLiveData<>();
    private LiveData<List<VistaAsignaturaAlumno>> listaAsignaturasConAlumnos;

    public ActivityVistasViewModel(@NonNull Application application) {
        super(application);

        if (repo == null) {
            repo = new ColegioRepositorio(application);
            listaAsignaturasConAlumnos = Transformations.switchMap(numeroSeleccion, number ->
                    repo.getVista_AsignaturasConAlumnos(number));
        }
    }


    public LiveData<Long> getNumero() {
        return numeroSeleccion;
    }

    public void setNumero(Long numero) {

        numeroSeleccion.setValue(numero);
    }

    public LiveData<List<VistaAsignaturaAlumno>> getResultados() {
        return listaAsignaturasConAlumnos;
    }

}