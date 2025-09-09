package edu.overdrive.vistas_room.repos;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.overdrive.vistas_room.bbdd.Colegio;
import edu.overdrive.vistas_room.daos.AlumnoAsignaturaDAO;
import edu.overdrive.vistas_room.daos.AlumnoDAO;
import edu.overdrive.vistas_room.daos.AsignaturaDAO;
import edu.overdrive.vistas_room.entidades.Asignatura;
import edu.overdrive.vistas_room.relaciones.many_to_many.AlumnoConAsignaturas;
import edu.overdrive.vistas_room.relaciones.many_to_many.AsignaturaConAlumnos;
import edu.overdrive.vistas_room.vistas.VistaAsignaturaAlumno;

/**
 * En el repositorio no deben vivir objetos LiveData
 */
public class ColegioRepositorio {
    private static final String TAG = ColegioRepositorio.class.getSimpleName();
    private Colegio basedatos;
    private AlumnoDAO alumnoDAO;
    private AsignaturaDAO asignaturaDAO;
    private AlumnoAsignaturaDAO alumnoAsignaturaDAO;

    public ColegioRepositorio(Context application) {
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

    public LiveData<List<VistaAsignaturaAlumno>> getVista_AsignaturasConAlumnos(Long idAsignatura) {

        return asignaturaDAO.getVista_AsignaturasConAlumnos(idAsignatura);
    }

    public void insertar(Asignatura dato) {
        Colegio.servicioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                asignaturaDAO.insertar(dato);
            }
        });
    }

}