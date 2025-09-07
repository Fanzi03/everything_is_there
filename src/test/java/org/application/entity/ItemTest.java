package org.application.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
