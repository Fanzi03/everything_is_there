package org.application.entity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.EnumSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.application.enums.ItemTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@Timeout(value = 1, unit = TimeUnit.SECONDS)
public class ItemTest {

	Item item;
	UUID id;

	@BeforeEach
	void setUp (){
		item = new Item();
		id = UUID.randomUUID();
	}

	@Test
	@DisplayName("should create item with builder from lombok")
	void builderfieldsTest(){
		item = Item.builder().name("first item").primaryTag(ItemTag.NEW).id(id).description("this is world").build();
		assertEquals("first item", item.getName());
		assertEquals(id, item.getId());
		assertEquals(item.getPrimaryTag(), ItemTag.NEW);
		assertThat(item.getDescription()).isEqualTo("this is world");
	}

	@Test
	@DisplayName("should be null")
	void nullAndEmptyfieldsTest(){
		assertNull(item.getName());
		assertNull(item.getId());
		assertNull(item.getPrimaryTag());
		assertNull(item.getDescription());
		// check type
		assertThat(item.getTags()).isEqualTo(EnumSet.noneOf(ItemTag.class));

	}

	@Test
	@DisplayName("check lombok")
	void shouldWorkWithBuild(){

		item = Item.builder().primaryTag(ItemTag.NEW).tags(null).build();
		assertThat(item.getTags()).contains(ItemTag.NEW);
	}

	@ParameterizedTest
	@EnumSource(ItemTag.class)
	@DisplayName("adding the primaryTag and it must be in Set")
	void shouldAddTagInSet(ItemTag tag){
		item.setPrimaryTag(tag);

		Item itemWithBuild = Item.builder().name("test").primaryTag(tag).build();

		assertThat(item.getTags()).contains(tag);
		assertThat(itemWithBuild.getTags()).contains(tag);
	}


	@ParameterizedTest
	@EnumSource(ItemTag.class)
	@DisplayName("deleting the tag and it must be deleting in primaryTag")
	void shouldDeletePrimaryTagFromSet(ItemTag tag){
		item.setPrimaryTag(tag);
		item.removeTag(tag);

		assertNull(item.getPrimaryTag());

		Item itemWithBuild = Item.builder().name("test").primaryTag(tag).build().setId(id);;

		itemWithBuild.removeTag(tag);
		assertNull(itemWithBuild.getPrimaryTag());

	}


}
