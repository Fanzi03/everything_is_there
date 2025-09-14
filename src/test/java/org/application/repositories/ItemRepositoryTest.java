package org.application.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

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

@SpringBootTest 
@Timeout(value = 15, unit = TimeUnit.SECONDS)
@Testcontainers
@Transactional
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
	}


	@Test
	void seachTest(){
		item = Item.builder().primaryTag(ItemTag.POPULAR).name("test naem").description("maybe elastic search").build();	
		item2 = Item.builder().primaryTag(ItemTag.POPULAR).name(item.getName()).description("cool day").build();

		itemRepository.save(item);
		itemRepository.save(item2);

		Page<Item> result = itemRepository.search("search", ItemTag.POPULAR.toString(), PageRequest.of(0,10)); 

		assertThat(result.getTotalElements()).isEqualTo(1);
		assertThat("maybe elastic search").isEqualTo(result.getContent().getFirst().getDescription());
		assertThat("POPULAR").isEqualTo(result.getContent().getFirst().getPrimaryTag().toString());
	}


}
