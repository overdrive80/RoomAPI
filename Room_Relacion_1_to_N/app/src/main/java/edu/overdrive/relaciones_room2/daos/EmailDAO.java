package edu.overdrive.relaciones_room2.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.overdrive.relaciones_room2.entidades.Email;

@Dao
public interface EmailDAO {

    // INSERTAR
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(Email perfil);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertarTodos(List<Email> perfil);

    // CONSULTAR
    @Query("SELECT * FROM email WHERE id_email = :id")
    LiveData<Email> getItemPorId(Integer id);

    @Query("SELECT * FROM email ORDER BY email ASC")
    LiveData<List<Email>> getTodosOrdenados();

    // ACTUALIZAR
    @Update
    int actualizar(Email perfil);

    @Update
    int actualizarTodos(List<Email> perfil);

    // BORRAR
    @Delete
    int borrar(Email perfil);

    @Delete
    int borrarTodos(List<Email> perfil);

    @Query("DELETE FROM email WHERE id_email = :id")
    int borrarPorId(Long id);

}
