package org.application.services;

import java.util.UUID;

import org.application.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface ItemService{

	Item save (Item item); 
	Item findById (UUID id);
	Item update (Item item, UUID id);
	void delete (UUID id);
	Page<Item> getAll (Pageable page);

}
