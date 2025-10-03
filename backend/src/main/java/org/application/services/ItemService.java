package org.application.services;

import java.util.UUID;

import org.application.criteria.ItemSearchCriteria;
import org.application.entity.Item;
import org.application.enums.ItemTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface ItemService{

	Item save (Item item); 
	Item findById (UUID id);
	Page<Item> findAllByPrimaryTag(ItemTag tag, Pageable pageable);
	Page<Item> findAllByDescription(String description, Pageable pageable);
	Page<Item> search(ItemSearchCriteria itemsr);
	Item update (Item item, UUID id);
	void delete (UUID id);
	Page<Item> getAll (Pageable page);

}
