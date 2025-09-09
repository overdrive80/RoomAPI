package com.develou.shoppinglist.data.relationentities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.develou.shoppinglist.data.entities.Item;
import com.develou.shoppinglist.data.entities.ShoppingList;
import com.develou.shoppinglist.data.entities.ShoppingListItem;

import java.util.List;

public class ShoppingListWithItems {
    @Embedded
    public ShoppingList shoppingList;

    @Relation(
		parentColumn = "shopping_list_id",    // Columna en ShoppingList (PK)
		entityColumn = "item_id",             // Columna en Item (PK)  
		associateBy = @Junction(ShoppingListItem.class) // Tabla intermedia
    )
    public List<Item> items;
}

/*
¿Cómo funciona Room internamente?
Cuando ejecutas esta consulta, Room hace 2 queries:

Primero: Obtiene las listas de compras

	SELECT * FROM shopping_list;

Segundo: Para cada lista, obtiene sus ítems usando la tabla intermedia

	SELECT i.* FROM item i
	INNER JOIN shopping_list_item sli ON i.item_id = sli.item_id
	WHERE sli.shopping_list_id = ?;

*/