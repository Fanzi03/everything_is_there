package org.application.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.application.entity.Item;
import org.application.enums.ItemTag;
import org.application.mapping.impl.ItemMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@Timeout(value = 3, unit = TimeUnit.SECONDS)
@ExtendWith(MockitoExtension.class)
public class ItemMapperTest {

	ItemMapperImpl itemMapper;	
	ItemDataTransferObject itemDto;
	Item item;
	UUID id;
	List<String> tagsD = Arrays.stream(ItemTag.values())
			.map(ItemTag::toString)
			.collect(Collectors.toList());

	Set<ItemTag> tagsE = Arrays.stream(ItemTag.values()).collect(Collectors.toSet());

	@BeforeEach
	void setUp(){
		itemMapper = new ItemMapperImpl();
		item = new Item();
		itemDto = new ItemDataTransferObject();
		id = UUID.randomUUID();
	}

	@Nested
	@DisplayName("toEntity() tests")
	class toEntityTests{

		@Test
		@DisplayName("mapAllFieldsAndIgnoreId")
		void shouldMapAllField(){

			itemDto = ItemDataTransferObject.builder()
				.primaryTag(ItemTag.NEW.toString())
				.tags(tagsD)
				.name("test").id(id)
				.description("1")
				.build();

			// check lowercase too
			itemDto.getTags().add("new");

			item = itemMapper.toEntity(itemDto);

			assertThat(item.getDescription()).isEqualTo(itemDto.getDescription());
			assertThat(item.getPrimaryTag().toString()).isEqualTo(itemDto.getPrimaryTag());
			assertThat(itemDto.getName()).isEqualTo(item.getName());
			assertThat(item.getId()).isNotEqualTo(item.getName());

			assertThat(itemDto.getTags()).containsAll(item.getTags().stream().map(ItemTag::toString).toList());
		}

		@Test
		@DisplayName("mapNull")
		void shouldReturnNullIfDtoNull(){
			item = itemMapper.toEntity(null);
			assertThat(item).isNull();
		}
	}


	@Nested
	@DisplayName("toDto() tests")
	class toDtoTests{

		@Test 
		@DisplayName("mapAllFields")
		void toDto(){

			item = Item.builder()
				.primaryTag(ItemTag.NEW)
				.id(id).name("test")
				.tags(tagsE)
				.description("1c")
				.build();

			itemDto = itemMapper.toDto(item);

			assertThat(itemDto.getPrimaryTag()).isEqualTo(item.getPrimaryTag().toString());
			assertThat(itemDto.getName()).isEqualTo(item.getName());
			assertThat(itemDto.getId()).isEqualTo(item.getId());
			assertThat(itemDto.getTags().stream().map(s -> ItemTag.fromString(s)).collect(Collectors.toSet()))
				.containsAll(item.getTags());
			assertThat(itemDto.getDescription()).isEqualTo(item.getDescription());
		}

		@Test
		@DisplayName("mapNull")
		void shouldReturnNullIfDtoNull(){
			itemDto = itemMapper.toDto(null);
			assertThat(itemDto).isNull();
		}
	}


}
