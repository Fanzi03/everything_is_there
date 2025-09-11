package org.application.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.application.enums.ItemTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Timeout(value = 2, unit = TimeUnit.SECONDS)
public class ItemDataTransferObjectTest {

	ItemDataTransferObject itemDto;	

	@BeforeEach
	void setUp(){
		itemDto = new ItemDataTransferObject();
	}

	@Test
	@DisplayName("testField")
	void testField(){
		List<String> tags = List.of(ItemTag.NEW.toString(), ItemTag.SALE.toString());

		itemDto = ItemDataTransferObject.builder().primaryTag(ItemTag.NEW.toString()).name("test").id(UUID.randomUUID())
			.tags(tags)
			.build();

		assertThat("test").isEqualTo(itemDto.getName());
		assertThat(itemDto.getId()).isNotNull();		
		assertThat(itemDto.getPrimaryTag()).isEqualTo(ItemTag.NEW.toString());
		assertThat(itemDto.getTags()).containsAll(tags);
	}


}
