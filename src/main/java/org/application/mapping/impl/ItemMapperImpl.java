package org.application.mapping.impl;

import java.util.stream.Collectors;

import org.application.entity.Item;
import org.application.enums.ItemTag;
import org.application.mapping.ItemDataTransferObject;
import org.application.mapping.ItemMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemMapperImpl implements ItemMapper{

	public Item toEntity(ItemDataTransferObject itemDto){
		if(itemDto==null) return null;
		return Item.builder()
			.name(itemDto.getName())
			.primaryTag(itemDto.getPrimaryTag() != null ? ItemTag.valueOf(itemDto.getPrimaryTag()) : null)
			.tags(itemDto.getTags().stream().map(s -> ItemTag.fromString(s)).collect(Collectors.toSet()))
			.description(itemDto.getDescription())
			.build();	
	}	

	public ItemDataTransferObject toDto(Item item){
		if(item==null) return null;
		return ItemDataTransferObject.builder()
			.primaryTag(item.getPrimaryTag() == null ? null: item.getPrimaryTag().toString())
			.name(item.getName())
			.tags(item.getTags().stream().map(ItemTag::toString).collect(Collectors.toList()))
			.id(item.getId())
			.description(item.getDescription())
			.build();
	}
}
