package org.application.services.impl;

import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.application.criteria.ItemSearchCriteria;
import org.application.entity.Item;
import org.application.enums.ItemTag;
import org.application.repositories.ItemRepository;
import org.application.services.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	public Page<Item> search(ItemSearchCriteria itemsr){
		var pageable = PageRequest.of(itemsr.getPage(), itemsr.getSize());

		if(!itemsr.hasPrimaryTag() && !itemsr.hasDescription() && !itemsr.hasTags()){
			throw new IllegalArgumentException("search without tag and description cannot being");
		}

		Set<ItemTag> tags = null;

		if(itemsr.hasTags()) {
			var ts = itemsr.getTags();
			if(ts != null && !ts.isEmpty()){
				tags = ts.stream().map(ItemTag::fromString).collect(Collectors.toCollection(
						() -> EnumSet.noneOf(ItemTag.class)
					)
				);
			}
		}

		if(itemsr.hasPrimaryTag()){ 
			var primaryTag = ItemTag.fromString(itemsr.getPrimaryTag());
			return itemRepository.search(itemsr.getDescription(), primaryTag,
			tags,
			pageable);
		}

		return itemRepository.search(itemsr.getDescription(), null, tags, pageable);
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
