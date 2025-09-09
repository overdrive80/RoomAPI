package edu.overdrive.relaciones_room3;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import edu.overdrive.relaciones_room3.entidades.Alumno;
import edu.overdrive.relaciones_room3.entidades.Asignatura;
import edu.overdrive.relaciones_room3.relaciones.many_to_many.AlumnoConAsignaturas;
import edu.overdrive.relaciones_room3.relaciones.many_to_many.AsignaturaConAlumnos;
import edu.overdrive.relaciones_room3.repos.ColegioRepositorio;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private TextView tvAlumnoAsignaturas, tvAsignaturaAlumnos;
    private ColegioRepositorio repo;
    private MainActivityViewModel viewModel;
    private Button btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this)
                .get(MainActivityViewModel.class);
        btnCerrar = findViewById(R.id.btnCerrar);
        btnCerrar.setOnClickListener(v -> finish());

        /******ALUMNO CON ASIGNATURAS************/
        tvAlumnoAsignaturas = findViewById(R.id.tvAlumnoAsignaturas);

        // Configuramos el observador del ViewModel: alumno --> Asignaturas
        final Observer<List<AlumnoConAsignaturas>> observadorAlumnoAsignaturas = getListObserverAlumnos();
        viewModel.getAlumnosConAsignaturas().observe(this, observadorAlumnoAsignaturas);

        /******ASIGNATURA CON ALUMNOS*********/
        tvAsignaturaAlumnos = findViewById(R.id.tvAsignaturaAlumnos);

        // Configuramos el observador del ViewModel: Asignatura --> Alumnos
        final Observer<List<AsignaturaConAlumnos>> observadorAsignaturaAlumnos = getListObserverAsignaturas();
        viewModel.getAsignaturasConAlumnos().observe(this, observadorAsignaturaAlumnos);
    }

    @NonNull
    private Observer<List<AlumnoConAsignaturas>> getListObserverAlumnos() {
        final Observer<List<AlumnoConAsignaturas>> observador = new Observer<List<AlumnoConAsignaturas>>() {
            @Override
            public void onChanged(List<AlumnoConAsignaturas> listado) {
                String nuevo = "Texto inicial";
                SpannableStringBuilder sb = new SpannableStringBuilder();

                if (listado == null || listado.isEmpty()) {
                    tvAlumnoAsignaturas.setText("No hay datos");
                    return;
                }

                for (AlumnoConAsignaturas alumnoConAsignaturas : listado) {
                    Alumno alumno = alumnoConAsignaturas.alumno;
                    List<Asignatura> asignaturas = alumnoConAsignaturas.asignaturas;

                    String msg = String.format("%s (%s):%n",
                            alumno.nombre, alumno.email);
                    sb.append(msg);

                    for (Asignatura asignatura : asignaturas) {
                        String texto = String.format("\t%s - Curso: %s%n",
                                asignatura.nombre, asignatura.curso);

                        SpannableString span = new SpannableString(texto);
                        span.setSpan(new StyleSpan(Typeface.ITALIC), 0, texto.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        sb.append(span);
                    }
                    sb.append("\n");
                }

                tvAlumnoAsignaturas.setText(sb);
            }
        };
        return observador;
    }

    @NonNull
    private Observer<List<AsignaturaConAlumnos>> getListObserverAsignaturas() {
        final Observer<List<AsignaturaConAlumnos>> observador = new Observer<List<AsignaturaConAlumnos>>() {
            @Override
            public void onChanged(List<AsignaturaConAlumnos> listado) {
                String nuevo = "Texto inicial";
                SpannableStringBuilder sb = new SpannableStringBuilder();

                if (listado == null || listado.isEmpty()) {
                    tvAsignaturaAlumnos.setText("No hay datos");
                    return;
                }

                for (AsignaturaConAlumnos asignaturaConAlumnos : listado) {
                    Asignatura asignatura = asignaturaConAlumnos.asignatura;
                    List<Alumno> alumnos = asignaturaConAlumnos.alumnos;

                    String msg = String.format("%s - Curso: %s%n",
                            asignatura.nombre, asignatura.curso);

                    sb.append(msg);

                    for (Alumno alumno : alumnos) {
                        String texto = String.format("\t%s (%s)%n",
                                alumno.nombre, alumno.email);

                        SpannableString span = new SpannableString(texto);
                        span.setSpan(new StyleSpan(Typeface.ITALIC), 0, texto.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        sb.append(span);
                    }
                    sb.append("\n");
                }

                tvAsignaturaAlumnos.setText(sb);
            }
        };
        return observador;
    }
}