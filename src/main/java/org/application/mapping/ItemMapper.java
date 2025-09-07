package org.application.mapping;

import org.application.entity.Item;

public interface ItemMapper {

	Item toEntity(ItemDataTransferObject itemDto);
	ItemDataTransferObject toDto(Item item);
	
}
