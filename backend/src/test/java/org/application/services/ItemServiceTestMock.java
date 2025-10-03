package org.application.services;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

import org.application.criteria.ItemSearchCriteria;
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
public class ItemServiceTestMock {

	@Mock
	ItemRepository itemRepository;

	@InjectMocks
	ItemServiceImpl itemService;

	Set<ItemTag> tagsE = Arrays.stream(ItemTag.values()).collect(Collectors.toSet());

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
	void searchTest(){
		var itemsr = ItemSearchCriteria.builder().description("default").primaryTag(ItemTag.NEW.toString())
			.size(10).page(0).build();
		var item = Item.builder().name("test").description("default").primaryTag(ItemTag.NEW).build();

		Pageable pageable = PageRequest.of(itemsr.getPage(), itemsr.getSize());
		Page<Item> mockPage = new PageImpl<>(List.of(item), pageable, 1);

		when(itemRepository.search(
			itemsr.getDescription(), ItemTag.fromString(itemsr.getPrimaryTag()), null , pageable)
		).thenReturn(mockPage);

		Page<Item> result = itemService.search(itemsr);

		assertThat(1).isEqualTo(result.getTotalElements());
		assertThat("NEW").isEqualTo(result.getContent().getFirst().getPrimaryTag().toString());
		assertThat("default").isEqualTo(result.getContent().getFirst().getDescription().toString());


		//only primaryTag
		var itemsCP = ItemSearchCriteria.builder()
			.primaryTag(ItemTag.NEW.toString())
			.build();
		when(itemRepository.search(null, ItemTag.fromString(itemsCP.getPrimaryTag()), null, pageable)).thenReturn(mockPage);

		Page<Item> primaryTagResult = itemService.search(itemsCP);

		assertThat(1).isEqualTo(primaryTagResult.getTotalElements());
		assertThat("NEW").isEqualTo(primaryTagResult.getContent().getFirst().getPrimaryTag().toString());

		//bad result
		var itemsCBad = ItemSearchCriteria.builder()
			.build();
		assertThrows(IllegalArgumentException.class, () -> itemService.search(itemsCBad));

	}

	/*
	@Test
	void testSearchOnlyByTags(){
		var item1 = Item.builder().name("test").description("pu").primaryTag(ItemTag.POPULAR).build();

		var itemsTags = ItemSearchCriteria.builder()
			.tags(List.of("POPULAR"))
			.size(10)
			.page(0)
			.build();

		Pageable pageable = PageRequest.of(itemsTags.getPage(), itemsTags.getSize());

		Page<Item> mockPageWithPopularTag = new PageImpl<>(List.of(item1), pageable, 1);

		Set<ItemTag> expectedSet = itemsTags.getTags().stream().map(ItemTag::fromString).collect(Collectors.toSet());

		//when(itemRepository.search(itemsTags.getDescription(), null, 
		//	expectedSet, pageable))
		//.thenReturn(mockPageWithPopularTag);
		when(itemRepository.search(isNull(), isNull(), eq(expectedSet), eq(pageable)))
    			.thenReturn(mockPageWithPopularTag);


		Page<Item> tagsResult = itemService.search(itemsTags);

		assertThat(1).isEqualTo(tagsResult.getTotalElements());
		//assertThat(tagsResult.getContent().getFirst().getTags()).containsExactlyInAnyOrder(ItemTag.POPULAR);
	}
	*/

	@Test
	void testOnlyDescriptionInSearch(){
		var itemsr = ItemSearchCriteria.builder().description("default").primaryTag(ItemTag.NEW.toString())
			.size(10).page(0).build();
		var item = Item.builder().name("test").description("default").primaryTag(ItemTag.NEW).build();

		Pageable pageable = PageRequest.of(itemsr.getPage(), itemsr.getSize());
		Page<Item> mockPage = new PageImpl<>(List.of(item), pageable, 1);

		//only description
		var itemsCB = ItemSearchCriteria.builder()
			.description("default")	
			.build();
		when(itemRepository.search(itemsCB.getDescription(),null, null ,pageable)).thenReturn(mockPage);

		Page<Item> descriptionResult = itemService.search(itemsCB);

		assertThat(1).isEqualTo(descriptionResult.getTotalElements());
		assertThat("default").isEqualTo(descriptionResult.getContent().getFirst().getDescription().toString());
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
