package org.application.services;

import java.util.UUID;

import org.application.entity.Item;

public interface ItemService{

	// delete, update, findById
	Item save (Item item); 
	Item findById (UUID id);
	Item update (Item item, UUID id);
	void delete (UUID id);
}
