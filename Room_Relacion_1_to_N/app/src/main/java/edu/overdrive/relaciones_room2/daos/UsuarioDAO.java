package edu.overdrive.relaciones_room2.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import edu.overdrive.relaciones_room2.entidades.Usuario;
import edu.overdrive.relaciones_room2.relaciones.one_to_many.RelacionUsuarioEmail;

@Dao
public interface UsuarioDAO {

    // INSERTAR
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(Usuario usuario);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertarTodos(List<Usuario> usuarios);

    // CONSULTAR
    @Query("SELECT * FROM usuario WHERE id_usuario = :id")
    LiveData<Usuario> getItemPorId(Integer id);

    @Query("SELECT * FROM usuario ORDER BY nombre ASC")
    LiveData<List<Usuario>> getTodosOrdenados();

    // ACTUALIZAR
    @Update
    int actualizar(Usuario usuario);

    @Update
    int actualizarTodos(List<Usuario> usuarios);

    // BORRAR
    @Delete
    int borrar(Usuario usuario);

    @Delete
    int borrarTodos(List<Usuario> usuarios);

    @Query("DELETE FROM usuario WHERE id_usuario = :id")
    int borrarPorId(Integer id);

    // RELACIONES
    @Transaction
    @Query("SELECT * FROM usuario")
    LiveData<List<RelacionUsuarioEmail>> getUsuariosConEmails();

}