package org.application.services.impl;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.application.entity.Item;
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

	@Override
	public Page<Item> getAll(Pageable page){
		return itemRepository.findAll(page);
	}
	@Override
	public Item save(Item item){
		return itemRepository.save(item);
	}

	@Override
	public Item findById(UUID id){
		return itemRepository.findById(id).orElseThrow(NoSuchElementException::new);
	}

	@Override
	public Item update (Item item, UUID id){
		Item foundItem = findById(id);
		if(!(item.getName().equals(foundItem.getName()))){
			return save(item);
		}

		return foundItem;
	}

	@Override
	public void delete(UUID id){
		findById(id);
		itemRepository.deleteById(id);
	}

}
