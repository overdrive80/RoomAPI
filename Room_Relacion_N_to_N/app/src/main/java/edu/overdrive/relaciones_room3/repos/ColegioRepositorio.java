package edu.overdrive.relaciones_room3.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.overdrive.relaciones_room3.bbdd.Colegio;
import edu.overdrive.relaciones_room3.daos.AlumnoAsignaturaDAO;
import edu.overdrive.relaciones_room3.daos.AlumnoDAO;
import edu.overdrive.relaciones_room3.daos.AsignaturaDAO;
import edu.overdrive.relaciones_room3.relaciones.many_to_many.AlumnoConAsignaturas;
import edu.overdrive.relaciones_room3.relaciones.many_to_many.AsignaturaConAlumnos;

/**
 * En el repositorio no deben vivir objetos LiveData
 */
public class ColegioRepositorio {
    private static final String TAG = ColegioRepositorio.class.getSimpleName();
    public Colegio basedatos;
    private AlumnoDAO alumnoDAO;
    private AsignaturaDAO asignaturaDAO;
    private AlumnoAsignaturaDAO alumnoAsignaturaDAO;

    public ColegioRepositorio(Application application) {
        basedatos = Colegio.getInstance(application);
        alumnoDAO = basedatos.alumnoDAO();
        asignaturaDAO = basedatos.asignaturaDAO();
        alumnoAsignaturaDAO = basedatos.alumnoAsignaturaDAO();
    }

    // Alumno --> Asignaturas
    public LiveData<List<AlumnoConAsignaturas>> getAlumnosConAsignaturas() {
        return alumnoDAO.getAlumnosConAsignaturas();
    }

    //Asignatura --> Alumnos
    public LiveData<List<AsignaturaConAlumnos>> getAsignaturasConAlumnos() {
        return asignaturaDAO.getAsignaturasConAlumnos();
    }

}