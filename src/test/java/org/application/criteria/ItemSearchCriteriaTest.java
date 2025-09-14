package org.application.criteria;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.application.enums.ItemTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import lombok.experimental.ExtensionMethod;

@Timeout(value = 4, unit = TimeUnit.SECONDS)
@ExtensionMethod(ItemSearchCriteria.class)
public class ItemSearchCriteriaTest {
	
	ItemSearchCriteria itemsc;

	@BeforeEach
	void setUp(){
		itemsc = ItemSearchCriteria.builder()
			.description("test1").primaryTag(ItemTag.NEW.toString()).size(10).page(0)
			.build();
	}
	
	@Test void testField(){
		assertThat(itemsc.getDescription()).isEqualTo("test1");
		assertThat(itemsc.getPrimaryTag()).isEqualTo(ItemTag.NEW.toString());
		assertThat(itemsc.getSize()).isEqualTo(10);
		assertThat(itemsc.getPage()).isEqualTo(0);
	}

	@Test void hasFieldTest(){
		assertTrue(itemsc.hasDescription());
		assertTrue(itemsc.hasPrimaryTag());
	}
		
}
