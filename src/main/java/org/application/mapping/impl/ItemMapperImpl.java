package org.application.mapping.impl;

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
			.build();	
	}	

	public ItemDataTransferObject toDto(Item item){
		if(item==null) return null;
		return ItemDataTransferObject.builder()
			.primaryTag(item.getPrimaryTag() == null ? null: item.getPrimaryTag().toString())
			.name(item.getName())
			.id(item.getId())
			.build();
	}
}
