package org.application.services.impl;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import org.application.entity.Item;
import org.application.enums.ItemTag;
import org.application.repositories.ItemRepository;
import org.application.services.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

	ItemRepository itemRepository;

	public Page<Item> getAll(Pageable page){
		return itemRepository.findAll(page);
	}

	public Item save(Item item){
		return itemRepository.save(item);
	}

	public Item findById(UUID id){
		return itemRepository.findById(id).orElseThrow(NoSuchElementException::new);
	}

	public Page<Item> findAllByPrimaryTag(ItemTag primaryTag, Pageable pageable){
		return itemRepository.findAllByPrimaryTag(primaryTag, pageable);
	}

	public Page<Item> findAllByDescription(String description, Pageable pageable){
		return itemRepository.findAllByDescription(description, pageable);
	}

	public Item update (Item item, UUID id){
		Item foundItem = findById(id);
		boolean hasChanged = false;

		if(!Objects.equals(foundItem.getPrimaryTag(), item.getPrimaryTag())){
			foundItem.setPrimaryTag(item.getPrimaryTag());
			hasChanged = true;
		}

		if(!Objects.equals(item.getName(),foundItem.getName())){
			foundItem.setName(item.getName());
			hasChanged = true;
		}

		return hasChanged ? save(foundItem) : foundItem;
	}

	public void delete(UUID id){
		findById(id);
		itemRepository.deleteById(id);
	}

}
