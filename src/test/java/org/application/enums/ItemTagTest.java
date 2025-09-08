package org.application.enums;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ItemTagTest {

	@Test
	@DisplayName("2025-09-07")
	void shouldHaveActualTag(){
		assertThat(ItemTag.valueOf("NEW")).isSameAs(ItemTag.NEW);
		assertThat(ItemTag.valueOf("POPULAR")).isSameAs(ItemTag.POPULAR);
	}
}
