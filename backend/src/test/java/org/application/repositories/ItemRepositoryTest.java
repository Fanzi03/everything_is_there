package org.application.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.application.BaseIntegrationTest;
import org.application.entity.Item;
import org.application.enums.ItemTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest 
@Timeout(value = 60, unit = TimeUnit.SECONDS)
@Transactional
@Testcontainers
public class ItemRepositoryTest extends BaseIntegrationTest{

	@Autowired
	ItemRepository itemRepository;

	Item item, item2;

	@BeforeEach
	void setUp(){
		item = new Item();
		item2 = new Item();
		itemRepository.deleteAll();
	}

	@Test void postgresContainerIsRunningAndServiceConnectedTest(){
		assertThat(postgresContainer.isRunning()).isTrue();
	}

	@Test
	@DisplayName("test primary_tag query")
	void findAllByPrimaryTagTest(){
		item = Item.builder().primaryTag(ItemTag.POPULAR).name("test naem").build();	
		item2 = Item.builder().primaryTag(ItemTag.NEW).name(item.getName()).build();
		itemRepository.save(item);
		itemRepository.save(item2);

		Page<Item> result = itemRepository.findAllByPrimaryTag(ItemTag.POPULAR, PageRequest.of(0, 10));
		
		assertEquals(1, result.getTotalElements());
		assertEquals("POPULAR", result.getContent().getFirst().getPrimaryTag().toString());
	}

	@Test
	@DisplayName("test query with description")
	void shouldReturnItemsByDescription(){
		item = Item.builder().primaryTag(ItemTag.POPULAR).name("test naem").description("maybe elastic search").build();	
		item2 = Item.builder().primaryTag(ItemTag.NEW).name(item.getName()).description("cool day").build();

		itemRepository.save(item);
		itemRepository.save(item2);

		Page<Item> result = itemRepository.findAllByDescription("maybe", PageRequest.of(0, 10));

		assertThat(result.getTotalElements()).isEqualTo(1);
		assertThat("maybe elastic search").isEqualTo(result.getContent().getFirst().getDescription());

		// null
		//Page<Item> resultEmpty = itemRepository.findAllByDescription(null, PageRequest.of(0,10));

		//log.info(resultEmpty + "{}");			
		//assertThat(result.getTotalElements()).isEqualTo(2);

	}


	@Test
	void seachTestWithoutTags(){
		item = Item.builder().primaryTag(ItemTag.POPULAR).name("test naem").description("maybe elastic search").build();	
		item2 = Item.builder().primaryTag(ItemTag.POPULAR).name(item.getName()).description("cool day").build();

		itemRepository.save(item);
		itemRepository.save(item2);

		Page<Item> result = itemRepository.search("search", ItemTag.POPULAR, null, PageRequest.of(0,10)); 

		assertThat(result.getTotalElements()).isEqualTo(1);
		assertThat("maybe elastic search").isEqualTo(result.getContent().getFirst().getDescription());
		assertThat("POPULAR").isEqualTo(result.getContent().getFirst().getPrimaryTag().toString());
	}

	@Test
	void seachTestSetTags(){
		Set<ItemTag> tagsE = Arrays.stream(ItemTag.values()).collect(Collectors.toSet());

		item = Item.builder().primaryTag(ItemTag.POPULAR).name("test naem").description("maybe elastic search").tags(tagsE).build();	
		item2 = Item.builder().primaryTag(ItemTag.POPULAR).name(item.getName()).description("cool day").build();

		itemRepository.save(item);
		itemRepository.save(item2);

		Page<Item> result = itemRepository.search("search", ItemTag.POPULAR, tagsE, PageRequest.of(0,10)); 

		assertThat(result.getTotalElements()).isEqualTo(1);
		assertThat("maybe elastic search").isEqualTo(result.getContent().getFirst().getDescription());
		assertThat("POPULAR").isEqualTo(result.getContent().getFirst().getPrimaryTag().toString());
	}

	@Test
	void seachTestSetTagsWithoutDescription(){
		Set<ItemTag> tagsE = Arrays.stream(ItemTag.values()).collect(Collectors.toSet());

		item = Item.builder().primaryTag(ItemTag.POPULAR).name("test naem").description("maybe elastic search").tags(tagsE).build();	
		item2 = Item.builder().primaryTag(ItemTag.POPULAR).name(item.getName()).description("cool day").build();
		var item5 = Item.builder().name("hello").build();

		itemRepository.save(item);
		itemRepository.save(item2);
		itemRepository.save(item5);

		Page<Item> result = itemRepository.search(null, ItemTag.POPULAR, tagsE, PageRequest.of(0,10)); 

		assertThat(result.getTotalElements()).isEqualTo(2);
		//assertThat("maybe elastic search").isEqualTo(result.getContent().getFirst().getDescription());
		assertThat("POPULAR").isEqualTo(result.getContent().getFirst().getPrimaryTag().toString());
	}

	@Test
	void seachTestOnlyDescription(){
		Set<ItemTag> tagsE = Arrays.stream(ItemTag.values()).collect(Collectors.toSet());

		item = Item.builder().primaryTag(ItemTag.POPULAR).name("test naem").description("maybe elastic search").tags(tagsE).build();	
		item2 = Item.builder().primaryTag(ItemTag.POPULAR).name(item.getName()).description("cool day").build();

		itemRepository.save(item);
		itemRepository.save(item2);

		Page<Item> result = itemRepository.search(item.getDescription(),null, null, PageRequest.of(0,10)); 


		assertThat(result.getTotalElements()).isEqualTo(1);
		assertThat("maybe elastic search").isEqualTo(result.getContent().getFirst().getDescription());
	}

	@Test
	void seachTestOnlyPrimaryTag(){
		Set<ItemTag> tagsE = Arrays.stream(ItemTag.values()).collect(Collectors.toSet());

		item = Item.builder().primaryTag(ItemTag.POPULAR).name("test naem").description("maybe elastic search").tags(tagsE).build();	
		item2 = Item.builder().primaryTag(ItemTag.POPULAR).name(item.getName()).description("cool day").build();

		itemRepository.save(item);
		itemRepository.save(item2);

		Page<Item> result = itemRepository.search(null, ItemTag.POPULAR, null, PageRequest.of(0,10)); 

		assertThat(result.getTotalElements()).isEqualTo(2);
	}


}
