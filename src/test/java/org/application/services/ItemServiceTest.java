package org.application.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.*;

import org.application.entity.Item;
import org.application.repositories.ItemRepository;
import org.application.services.impl.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

	@Mock
	ItemRepository itemRepository;

	@InjectMocks
	ItemServiceImpl itemService;

	Item item;
	UUID uuid;

	@BeforeEach
	void setUp(){
		item = new Item();
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

		when(itemRepository.save(updatedItem)).thenReturn(updatedItem);
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
