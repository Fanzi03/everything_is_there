package org.application.services;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

import org.application.entity.Item;
import org.application.enums.ItemTag;
import org.application.repositories.ItemRepository;
import org.application.services.impl.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.experimental.ExtensionMethod;

@ExtensionMethod(ItemServiceImpl.class)
@ExtendWith(MockitoExtension.class)
@Timeout(value = 5, unit = TimeUnit.SECONDS)
public class ItemServiceTest {

	@Mock
	ItemRepository itemRepository;

	@InjectMocks
	ItemServiceImpl itemService;

	Item item, item2;
	UUID uuid;

	@BeforeEach
	void setUp(){
		item = new Item();
		item2 = new Item();
		uuid = UUID.randomUUID();
	}

	@Test
	void saveTest(){
		when(itemRepository.save(item)).thenReturn(item);

		item.setName("Test item");
		Item itemFromService = itemService.save(item);
		assertEquals(item.getName(), itemFromService.getName());
	}

	@Test
	void findByIdTest(){
		when(itemRepository.findById(uuid)).thenReturn(Optional.empty());
		assertThrows(NoSuchElementException.class, () -> itemService.findById(uuid));

		item.setId(uuid);
		when(itemRepository.findById(uuid)).thenReturn(Optional.of(item));
		assertEquals(item, itemService.findById(uuid));
	}

	@Test
	@DisplayName("should return only with POPULAR tag")
	void findAllByPrimaryTag(){
		ItemTag needTag = ItemTag.POPULAR;

		item.setPrimaryTag(needTag);	
		item2.setPrimaryTag(ItemTag.NEW);


		Pageable pageable = PageRequest.of(0, 10);
		Page<Item> mockPage = new PageImpl<>(List.of(item), pageable, 1);

		when(itemRepository.findAllByPrimaryTag(needTag, pageable)).thenReturn(mockPage);

		Page<Item> result = itemService.findAllByPrimaryTag(needTag,pageable);

		assertEquals(1, result.getTotalElements());
		assertEquals("POPULAR", result.getContent().get(0).getPrimaryTag().toString());
	}

	@Test
	void findAllByDescription(){
		item.setDescription("hello").setName("nice");
		Item item2 = new Item().setName("h").setDescription("util");

		Pageable pageable = PageRequest.of(0, 10);
		Page<Item> mockPage = new PageImpl<>(List.of(item), pageable, 1);	

		when(itemRepository.findAllByDescription("hel", pageable)).thenReturn(mockPage);

		Page<Item> result = itemService.findAllByDescription("hel", pageable);
		
		assertThat(result.getTotalElements()).isEqualTo(1);
		assertThat(result.getContent().getFirst().getDescription()).isEqualTo("hello");
	}

	@Test
	void getAll(){
		item.setName("test");
		itemService.save(item);

		Pageable pageable = PageRequest.of(0, 10);
		Page<Item> mockPage = new PageImpl<>(List.of(item), pageable, 1);

		when(itemRepository.findAll(pageable)).thenReturn(mockPage);

		Page<Item> result = itemService.getAll(pageable);

		assertEquals(1, result.getTotalElements());
		assertEquals("test", result.getContent().get(0).getName());
	}

	@Test
	void updateTest(){
		when(itemRepository.findById(uuid)).thenReturn(Optional.empty());
		assertThrows(NoSuchElementException.class, () -> itemService.update(item, uuid));

		item.setName("Test item with old name");	
		item.setId(uuid);
		when(itemRepository.findById(uuid)).thenReturn(Optional.of(item));

		Item updatedItem = new Item();
		updatedItem.setName("new item");

		when(itemRepository.save(any(Item.class))).thenReturn(item);
		Item result = itemService.update(updatedItem, uuid);
		assertEquals(updatedItem.getName(), result.getName());

	}


	@Test
	void deleteTest(){
		item.setId(uuid);

		when(itemRepository.findById(uuid)).thenReturn(Optional.of(item));
		itemService.delete(uuid);

		when(itemRepository.findById(uuid)).thenReturn(Optional.empty());
		assertThrows(NoSuchElementException.class, () -> itemService.delete(uuid));
	}

}
