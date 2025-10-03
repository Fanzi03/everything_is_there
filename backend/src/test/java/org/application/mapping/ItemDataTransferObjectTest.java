package org.application.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.application.enums.ItemTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

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
			.description("cool")
			.build();

		assertThat("test").isEqualTo(itemDto.getName());
		assertThat(itemDto.getId()).isNotNull();		
		assertThat(itemDto.getPrimaryTag()).isEqualTo(ItemTag.NEW.toString());
		assertThat(itemDto.getTags()).containsAll(tags);
		assertThat(itemDto.getDescription()).isEqualTo("cool");

	}

	@ParameterizedTest
	@EnumSource(ItemTag.class)
	@DisplayName("testBuilder")
	void shouldwork(ItemTag tag){
		itemDto = ItemDataTransferObject.builder().name("test").primaryTag(tag.toString()).tags(null).build();

		assertThat(itemDto.getTags()).contains(tag.toString());

		itemDto.removeTag(tag.toString());

		assertThat(itemDto.getTags()).isEqualTo(new ArrayList<>());
		assertThat(itemDto.getPrimaryTag()).isNull();

		itemDto = ItemDataTransferObject.builder().name("hello").primaryTag(tag.toString()).build();

		assertThat(itemDto.getTags()).contains(tag.toString());
	}
}
