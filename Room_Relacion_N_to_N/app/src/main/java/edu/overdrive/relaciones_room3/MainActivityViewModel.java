package edu.overdrive.relaciones_room3;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import edu.overdrive.relaciones_room3.relaciones.many_to_many.AlumnoConAsignaturas;
import edu.overdrive.relaciones_room3.relaciones.many_to_many.AsignaturaConAlumnos;
import edu.overdrive.relaciones_room3.repos.ColegioRepositorio;

public class MainActivityViewModel extends AndroidViewModel {

    private ColegioRepositorio repo;
    private LiveData<List<AlumnoConAsignaturas>> listaAlumnosConAsignaturas =
            new MutableLiveData<>(new ArrayList<>());

    private LiveData<List<AsignaturaConAlumnos>> listaAsignaturasConAlumnos =
            new MutableLiveData<>(new ArrayList<>());

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repo = new ColegioRepositorio(application);

        listaAlumnosConAsignaturas = repo.getAlumnosConAsignaturas();
        listaAsignaturasConAlumnos = repo.getAsignaturasConAlumnos();
    }

    // Alumno --> Asignaturas
    public LiveData<List<AlumnoConAsignaturas>> getAlumnosConAsignaturas() {
        return listaAlumnosConAsignaturas;
    }

    //Asignatura --> Alumnos
    public LiveData<List<AsignaturaConAlumnos>> getAsignaturasConAlumnos() {
        return listaAsignaturasConAlumnos;
    }
}