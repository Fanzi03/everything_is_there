package org.application.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class ItemTest {

	Item item;

	@BeforeEach
	void setUp (){
		item = new Item();
	}

	@Test
	@DisplayName("should create item with builder from lombok")
	void builderfieldsTest(){
		UUID id = UUID.randomUUID();
		Item item2 = Item.builder().name("first item").id(id).build();
		assertEquals("first item", item2.getName());
		assertEquals(id, item2.getId());
	}

	@Test
	@DisplayName("should be null")
	void nullfieldsTest(){
		assertNull(item.getName());
		assertNull(item.getId());
	}

	@Test
	@DisplayName("should ignore blanck")
	void blanckfieldsTest(){
		Item item2 = new Item();
		assertThrows(IllegalArgumentException.class, () -> item2.setName("  "));
	}
}
