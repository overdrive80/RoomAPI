package edu.overdrive.vistas_room;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import edu.overdrive.vistas_room.vistas.VistaAsignaturaAlumno;

public class ActivityVistas extends AppCompatActivity {

    private EditText etID;
    private TextView tvResultados;
    private ActivityVistasViewModel viewModel;
    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistas);

        inicializarVistas();
        observarViewModel();
        configurarBackPressed(); // Nueva configuración
    }

    private void inicializarVistas() {
        viewModel = new ViewModelProvider(this).get(ActivityVistasViewModel.class);

        etID = findViewById(R.id.etID);
        etID.addTextChangedListener(textWatcher);
        tvResultados = findViewById(R.id.tvResultados);
        btnSalir = findViewById(R.id.btnCerrar);
        btnSalir.setOnClickListener(v -> finish());
    }


    private void observarViewModel() {
        Observer<List<VistaAsignaturaAlumno>> observer = new Observer<List<VistaAsignaturaAlumno>>() {
            @Override
            public void onChanged(List<VistaAsignaturaAlumno> resultados) {
                StringBuilder sb = new StringBuilder();
                if (resultados != null && !resultados.isEmpty()) {
                    for (VistaAsignaturaAlumno resultado : resultados) {
                        sb.append("ID Asignatura: ").append(resultado.idAsignatura)
                                .append("\nAsignatura: ").append(resultado.nombreAsignatura)
                                .append("\nCurso: ").append(resultado.curso)
                                .append("\nID Alumno: ").append(resultado.idAlumno)
                                .append("\nAlumno: ").append(resultado.nombreAlumno)
                                .append("\nEmail: ").append(resultado.email)
                                .append("\n------------------------\n");
                    }
                } else {
                    sb.append("No se encontraron resultados para la búsqueda");
                }
                tvResultados.setText(sb.toString());

            }
        };

        viewModel.getResultados().observe(this, observer);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            if (valorValido(s)) {
                try {
                    Long numero = Long.parseLong(s.toString());
                    viewModel.setNumero(numero);
                } catch (NumberFormatException e) {
                    viewModel.setNumero(0L);
                }
            } else {
                viewModel.setNumero(0L);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    private boolean valorValido(Editable s) {
        return s != null && !s.toString().isEmpty();
    }

    private void ocultarTeclado() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    // Captura posibles eventos de retroceso de la app
    private void configurarBackPressed() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (etID.hasFocus()) {
                    // Si el EditText tiene foco, ocultar teclado y quitar foco
                    ocultarTeclado();
                    etID.clearFocus();
                } else {
                    // Comportamiento normal: cerrar la Activity
                    setEnabled(false); // Deshabilitar temporalmente el callback
                    getOnBackPressedDispatcher().onBackPressed(); // Ejecutar back normal
                }
            }
        });
    }
}

