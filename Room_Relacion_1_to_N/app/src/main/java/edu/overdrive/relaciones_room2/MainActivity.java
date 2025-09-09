package edu.overdrive.relaciones_room2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import edu.overdrive.relaciones_room2.entidades.Email;
import edu.overdrive.relaciones_room2.entidades.Usuario;
import edu.overdrive.relaciones_room2.relaciones.one_to_many.RelacionUsuarioEmail;
import edu.overdrive.relaciones_room2.repos.RedSocialRepositorio;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private TextView tvUsuarioEmails;
    private RedSocialRepositorio repo;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsuarioEmails = findViewById(R.id.tvUsuariosEmails);

        viewModel = new ViewModelProvider(this)
                .get(MainActivityViewModel.class);

        // Configuramos el observador del ViewModel
        final Observer<List<RelacionUsuarioEmail>> observador = getListObserver();
        viewModel.getUsuariosConEmails().observe(this, observador);
    }

    @NonNull
    private Observer<List<RelacionUsuarioEmail>> getListObserver() {
        final Observer<List<RelacionUsuarioEmail>> observador = new Observer<List<RelacionUsuarioEmail>>() {
            @Override
            public void onChanged(List<RelacionUsuarioEmail> relacion) {
                String nuevo = "Texto inicial";
                SpannableStringBuilder sb = new SpannableStringBuilder();

                if (relacion == null || relacion.isEmpty()) {
                    tvUsuarioEmails.setText("No hay datos");
                    return;
                }

                for (RelacionUsuarioEmail usuarioEmail : relacion) {
                    Usuario usuario = usuarioEmail.usuario;
                    List<Email> emails = usuarioEmail.listaEmails;

                    String msg = String.format("Usuario: %s:%n", usuario.nombre);
                    sb.append(msg);

                    for (Email email : emails) {
                        String texto = String.format("\tEmail: %s%n", email.email);

                        SpannableString span = new SpannableString(texto);
                        span.setSpan(new StyleSpan(Typeface.ITALIC), 0, texto.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        sb.append(span);
                    }
                    sb.append("\n");
                }

                tvUsuarioEmails.setText(sb);
            }
        };
        return observador;
    }
}