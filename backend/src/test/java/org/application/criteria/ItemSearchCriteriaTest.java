package org.application.criteria;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.application.enums.ItemTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import lombok.experimental.ExtensionMethod;

@Timeout(value = 4, unit = TimeUnit.SECONDS)
@ExtensionMethod(ItemSearchCriteria.class)
public class ItemSearchCriteriaTest {
	
	ItemSearchCriteria itemsc;

	List<String> tagsD = Arrays.stream(ItemTag.values())
			.map(ItemTag::toString)
			.collect(Collectors.toList());

	Set<ItemTag> tagsE = Arrays.stream(ItemTag.values()).collect(Collectors.toSet());

	@BeforeEach
	void setUp(){
		itemsc = ItemSearchCriteria.builder()
			.description("test1").primaryTag(ItemTag.NEW.toString()).size(10).page(0)
			.build().setSize(11);
	}
	
	@Test void testField(){
		itemsc.setTags(tagsD);

		assertThat(itemsc.getTags()).containsAll(tagsD);
		assertThat(itemsc.getDescription()).isEqualTo("test1");
		assertThat(itemsc.getPrimaryTag()).isEqualTo(ItemTag.NEW.toString());
		assertThat(itemsc.getSize()).isEqualTo(11);
		assertThat(itemsc.getPage()).isEqualTo(0);
	}

	@Test void shouldNull(){
		var itemsr = new ItemSearchCriteria();
		assertNull(itemsr.getDescription());
	}

	@Test void hasFieldTest(){
		itemsc.setTags(tagsD);
		assertTrue(itemsc.hasDescription());
		assertTrue(itemsc.hasPrimaryTag());
		assertTrue(itemsc.hasTags());
	}
		
}
