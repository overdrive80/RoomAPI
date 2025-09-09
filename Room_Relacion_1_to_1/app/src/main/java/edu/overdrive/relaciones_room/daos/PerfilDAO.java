package edu.overdrive.relaciones_room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import edu.overdrive.relaciones_room.entidades.Perfil;
import edu.overdrive.relaciones_room.entidades.Usuario;
import edu.overdrive.relaciones_room.relaciones.one_to_one.RelacionPerfilUsuario;

@Dao
public interface PerfilDAO {

    // INSERTAR
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(Perfil perfil);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertarTodos(List<Perfil> perfil);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertarTodos(Perfil... perfiles);

    // CONSULTAR
    @Query("SELECT * FROM perfil WHERE id_perfil = :id")
    LiveData<Perfil> getItemPorId(Long id);

    @Query("SELECT * FROM perfil")
    LiveData<List<Perfil>> getTodos();

    // ACTUALIZAR
    @Update
    int actualizar(Perfil perfil);

    @Update
    int actualizarTodos(List<Perfil> perfil);

    // BORRAR
    @Delete
    int borrar(Perfil perfil);

    @Delete
    int borrarTodos(List<Perfil> perfil);

    @Query("DELETE FROM perfil WHERE id_perfil = :id")
    int borrarPorId(Long id);

    // RELACIONES
    @Transaction
    @Query("SELECT * FROM perfil")
    LiveData<List<RelacionPerfilUsuario>> getPerfilesConUsuarios();

}
