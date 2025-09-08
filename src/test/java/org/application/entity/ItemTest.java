package org.application.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.application.enums.ItemTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

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
		item = Item.builder().name("first item").primaryTag(ItemTag.NEW).id(id).build();
		assertEquals("first item", item.getName());
		assertEquals(id, item.getId());
		assertEquals(item.getPrimaryTag(), ItemTag.NEW);
	}

	@Test
	@DisplayName("should be null")
	void nullfieldsTest(){
		assertNull(item.getName());
		assertNull(item.getId());
		assertNull(item.getPrimaryTag());
	}


}
