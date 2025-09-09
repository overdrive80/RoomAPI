package edu.overdrive.relaciones_room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import edu.overdrive.relaciones_room.entidades.Perfil;
import edu.overdrive.relaciones_room.entidades.Usuario;
import edu.overdrive.relaciones_room.relaciones.one_to_one.RelacionPerfilUsuario;
import edu.overdrive.relaciones_room.repos.RedSocialRepositorio;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private TextView tvTexto;
    private RedSocialRepositorio repo;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTexto = findViewById(R.id.tvTexto);

        viewModel = new ViewModelProvider(this)
                .get(MainActivityViewModel.class);

        // Configuramos el observador del ViewModel
        final Observer<List<RelacionPerfilUsuario>> observador = getListObserver();
        viewModel.getPerfilesConUsuarios().observe(this, observador);
    }

    @NonNull
    private Observer<List<RelacionPerfilUsuario>> getListObserver() {
        final Observer<List<RelacionPerfilUsuario>> observador = new Observer<List<RelacionPerfilUsuario>>() {
            @Override
            public void onChanged(List<RelacionPerfilUsuario> relacionPerfilUsuarios) {
                String nuevo = "Texto inicial";
                StringBuilder sb = new StringBuilder();

                if (relacionPerfilUsuarios == null || relacionPerfilUsuarios.isEmpty()) {
                    tvTexto.setText("No hay datos");
                    return;
                }

                for (RelacionPerfilUsuario usuarioPerfil : relacionPerfilUsuarios) {
                    Perfil perfil = usuarioPerfil.perfil;
                    Usuario usuario = usuarioPerfil.usuario;

                    String msg = String.format("El perfil con %d está en la url %s, y pertenece al usuario " + "%s con número de id: %d.%n",
                            perfil.idPerfil, perfil.avatarUrl, usuario.nombre, usuario.idUsuario);

                    sb.append(msg);

                }

                tvTexto.setText(sb.toString());
            }
        };
        return observador;
    }
}