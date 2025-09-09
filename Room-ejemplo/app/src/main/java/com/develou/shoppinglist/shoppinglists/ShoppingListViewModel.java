package com.develou.shoppinglist.shoppinglists;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.develou.shoppinglist.Utils;
import com.develou.shoppinglist.data.ShoppingListRepository;
import com.develou.shoppinglist.data.entities.Collaborator;
import com.develou.shoppinglist.data.entities.Info;
import com.develou.shoppinglist.data.entities.Item;
import com.develou.shoppinglist.data.partialentities.ShoppingListId;
import com.develou.shoppinglist.data.partialentities.ShoppingListInsert;
import com.develou.shoppinglist.data.relationentities.ShoppingListWithCollaborators;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShoppingListViewModel extends AndroidViewModel {

    // Repositorio de listas de compras
    private final ShoppingListRepository mRepository;

    // Filtros observados
    private final MutableLiveData<List<String>> mCategories
            = new MutableLiveData<>(new ArrayList<>());

    // Listas de compras observadas
    private final LiveData<List<ShoppingListWithCollaborators>> mShoppingLists;

    // Filtros
    private final List<String> mFilters = new ArrayList<>();

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ShoppingListRepository(application);

        // Obtener listas de compras por categorías
        mShoppingLists = Transformations.switchMap(
                mCategories,
                categories -> {
                    if (categories.isEmpty()) {
                        return mRepository.getShoppingLists();
                    } else {
                        return mRepository.getShoppingListsWithCategories(categories);
                    }
                }
        );
    }

    public void insert(ShoppingListInsert shoppingList) {
        String infoCreatedDate = Utils.getCurrentDate();
        String colaboratorId = UUID.randomUUID().toString();
        String itemId = UUID.randomUUID().toString();

        // Preparar info
        Info info = new Info(shoppingList.id,
                infoCreatedDate, infoCreatedDate);

        // Preparar colaboradores
        Collaborator collaborator = new Collaborator(colaboratorId,
                "C-" + colaboratorId, shoppingList.id);
        List<Collaborator> collaborators = new ArrayList<>();
        collaborators.add(collaborator);

        // Preparar items
        Item item = new Item(itemId, "Ejemplo");
        List<Item> items = new ArrayList<>();
        items.add(item);

        // Insertar en repositorio
        mRepository.insert(shoppingList, info, collaborators, items);
    }

    public void addFilter(String category) {
        mFilters.add(category);
        mCategories.setValue(mFilters);
    }

    public void removeFilter(String category) {
        mFilters.remove(category);
        mCategories.setValue(mFilters);
    }

    public LiveData<List<ShoppingListWithCollaborators>> getShoppingLists() {
        return mShoppingLists;
    }

    public void markFavorite(String shoppingListId) {
        mRepository.markFavorite(shoppingListId);
    }

    public void deleteShoppingList(ShoppingListWithCollaborators shoppingList) {
        ShoppingListId id = new ShoppingListId(shoppingList.shoppingList.id);
        mRepository.deleteShoppingList(id);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
