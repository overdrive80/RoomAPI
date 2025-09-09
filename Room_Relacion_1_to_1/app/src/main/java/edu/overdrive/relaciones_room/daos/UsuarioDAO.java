package edu.overdrive.relaciones_room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.overdrive.relaciones_room.entidades.Usuario;

@Dao
public interface UsuarioDAO {

    // INSERTAR
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(Usuario usuario);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertarTodos(List<Usuario> usuarios);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertarTodos(Usuario... usuarios);

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

}