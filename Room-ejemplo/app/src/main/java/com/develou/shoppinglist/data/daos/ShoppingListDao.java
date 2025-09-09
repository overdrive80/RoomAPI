package com.develou.shoppinglist.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.develou.shoppinglist.data.entities.Collaborator;
import com.develou.shoppinglist.data.entities.Info;
import com.develou.shoppinglist.data.entities.ShoppingList;
import com.develou.shoppinglist.data.partialentities.ShoppingListId;
import com.develou.shoppinglist.data.partialentities.ShoppingListInsert;
import com.develou.shoppinglist.data.relationentities.ShoppingListWithCollaborators;
import com.develou.shoppinglist.data.relationentities.ShoppingListWithItems;

import java.util.List;

@Dao
public abstract class ShoppingListDao {
    @Transaction
    @Query("SELECT * FROM v_full_shopping_lists ORDER BY created_date DESC")
    public abstract LiveData<List<ShoppingListWithCollaborators>> shoppingLists();

    @Transaction
    @Query("SELECT * FROM shopping_list WHERE shopping_list_id = :id")
    public abstract LiveData<ShoppingListWithItems> shoppingListWithItems(String id);

    @Transaction
    @Query("SELECT * FROM v_full_shopping_lists WHERE category IN(:categories)" +
            "ORDER BY created_date DESC")
    public abstract LiveData<List<ShoppingListWithCollaborators>> getShoppingListsByCategories(List<String> categories);

    @Transaction
    public void insertWithInfoAndCollaborators(ShoppingListInsert shoppingList,
                                               Info info, List<Collaborator> collaborators) {
        insertShoppingList(shoppingList);
        insertInfo(info);
        insertAllCollaborators(collaborators);
    }

    @Transaction
    public void insertAllWithInfosAndCollaborators(List<ShoppingListInsert> shoppingLists,
                                                   List<Info> infos,
                                                   List<Collaborator> collaborators) {
        insertAll(shoppingLists);
        insertAllInfos(infos);
        insertAllCollaborators(collaborators);
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void insertAllCollaborators(List<Collaborator> collaborators);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertShoppingList(ShoppingList shoppingList);

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = ShoppingList.class)
    abstract void insertShoppingList(ShoppingListInsert shoppingList);

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = ShoppingList.class)
    abstract void insertAll(List<ShoppingListInsert> lists);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertInfo(Info info);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertAllInfos(List<Info> infos);

    @Transaction
    public void markFavorite(String id) {
        updateShoppingListFavorite(id);
        updateInfoLastUpdated(id);
    }

    @Query("UPDATE shopping_list SET is_favorite= NOT is_favorite WHERE shopping_list_id = :id")
    protected abstract void updateShoppingListFavorite(String id);

    @Query("UPDATE info SET last_updated = CURRENT_TIMESTAMP WHERE shopping_list_id=:shoppingListId")
    protected abstract void updateInfoLastUpdated(String shoppingListId);

    @Delete(entity = ShoppingList.class)
    public abstract void deleteShoppingList(ShoppingListId id);

    @Query("DELETE FROM shopping_list")
    public abstract void deleteAll();
}
