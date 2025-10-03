package org.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.application.BaseIntegrationTest;
import org.application.criteria.ItemSearchCriteria;
import org.application.entity.Item;
import org.application.enums.ItemTag;
import org.application.repositories.ItemRepository;
import org.application.services.impl.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Timeout(value = 15, unit = TimeUnit.SECONDS)
@Transactional
public class ItemServiceTestContainer extends BaseIntegrationTest{

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	ItemServiceImpl itemService;

	@BeforeEach void setUp(){
		itemRepository.deleteAll();
	}

	@Test void postgresContainerIsRunningAndServiceConnectedTest(){
		assertThat(postgresContainer.isRunning()).isTrue();
	}

	@Test void testSearchOnlyByTags(){
		var item1 = Item.builder().name("test").description("pufksdkflsdfjk").primaryTag(ItemTag.POPULAR).build();

		var itemsTags = ItemSearchCriteria.builder()
			.tags(List.of("POPULAR"))
			.primaryTag(item1.getPrimaryTag().toString())
			.build();

		itemRepository.save(item1);

		Page<Item> tagsResult = itemService.search(itemsTags);

		assertThat(1).isEqualTo(tagsResult.getTotalElements());
		assertTrue(tagsResult.getContent().getFirst().getTags() != null);
		//assertThat(tagsResult.getContent().getFirst().getTags()).containsExactlyInAnyOrder(ItemTag.POPULAR);
	}


	@Test void testSearch(){
		var item1 = Item.builder().name("test").description("description").primaryTag(ItemTag.NEW).build();

		var searchBody = ItemSearchCriteria.builder()
			.tags(List.of("NEW"))	
			.primaryTag(item1.getPrimaryTag().toString())
			.description("description")
			.build();

		itemRepository.save(item1);

//		Set<ItemTag> expectedSet = searchBody.getTags().stream().map(ItemTag::fromString).collect(Collectors.toCollection(() -> EnumSet.noneOf(ItemTag.class)));

		Page<Item> tagsResult = itemService.search(searchBody);
	
		assertThat(tagsResult.getContent().getFirst().getTags()).containsExactlyInAnyOrder(ItemTag.NEW);


	}













}
