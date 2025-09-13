package org.application.controllers;

import org.application.BaseIntegrationTest;
import org.application.entity.Item;
import org.application.enums.ItemTag;
import org.application.mapping.ItemDataTransferObject;
import org.application.mapping.impl.ItemMapperImpl;
import org.application.repositories.ItemRepository;
import org.application.services.impl.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) 
@Timeout(value = 10, unit = TimeUnit.SECONDS)
@Testcontainers
public class ItemControllerTest extends BaseIntegrationTest{

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	ItemMapperImpl itemMapper;

	@Autowired 
	ItemServiceImpl itemService;

	@LocalServerPort
	Integer port;

	@BeforeEach
	void setUp(){
		RestAssured.baseURI = "http://localhost:" + port;
		itemRepository.deleteAll();
	}

	@Test void postgresContainerIsRunningAndServiceConnectedTest(){
		assertThat(postgresContainer.isRunning()).isTrue();
	}

	@Test
	void whenMakingPostRequesTest(){
		ItemDataTransferObject itemRequest = new ItemDataTransferObject();
		itemRequest.setName("test item");
		itemRequest.setPrimaryTag("NEW");


		ItemDataTransferObject actualItem = given().contentType(ContentType.JSON).body(itemRequest)
		.when().post("/items").then().log().all().statusCode(200).extract().as(ItemDataTransferObject.class);

		assertThat(actualItem.getName()).isEqualTo(itemRequest.getName());
		assertThat(actualItem.getPrimaryTag()).isEqualTo(itemRequest.getPrimaryTag());
	}

	@Nested
	@DisplayName("GET")
	class GetTest{
		@Test
		void whenGetRequestTest(){
			Item itemRequest = new Item();
			itemRequest.setName("test item");
			itemRequest.setPrimaryTag(ItemTag.POPULAR);

			Item savedItem = itemRepository.save(itemRequest);	
			when().get("/items/" + savedItem.getId())
				.then().log().all()
				.statusCode(200)
				.and()
				.body("name", equalTo("test item"))
				.body("id", notNullValue())	
				.body("primaryTag", equalTo("POPULAR"));	

			// test the wrong id
			when().get("/items/" + UUID.randomUUID()).then().log().all()
				.statusCode(404);
		}

		@Test
		void whenGetAllByPrimaryTag(){
			// two times for add data
			createItemDto();
			createItemDto();

			//different tag
			itemRepository.save(Item.builder().primaryTag(ItemTag.NEW).name("test").build());

			given().contentType(ContentType.JSON)
				.queryParam("page", 0).queryParam("size", 10).queryParam("primary_tag", ItemTag.POPULAR.toString())
				.when().get("/items/search/tag").then().statusCode(200)
				.body("content", notNullValue())
				.body("content.size()", equalTo(2))
				.body("content[0].primaryTag", equalTo("POPULAR"));

			//test the wrong tag
			given().contentType(ContentType.JSON).queryParam("page", 0).queryParam("size", 10)
				.when().get("/items/search/tag/" + "unknown tag").then().statusCode(404);
		}

		@Test
		void whenGetAllByDescription(){

			ItemDataTransferObject item4 = createItemDto();

			for(int i = 0; i < 10; i++){
				itemRepository.save(
					Item.builder().description("for cooking"+i)
					.name("test").build()
				);
			}

			given().contentType(ContentType.JSON)
				.queryParam("page", 0).queryParam("size", 11).queryParam("description", item4.getDescription())
				.when().get("/items/search/desc").then().statusCode(200)
				.body("content", notNullValue())
				.body("content.size()", greaterThanOrEqualTo(0))
				.body("content[0].description", equalTo("for cooking"));
		}

		@Test
		void whenGetAllRequestTest(){
			Item itemRequest = itemRepository.save(
				Item.builder().primaryTag(ItemTag.POPULAR).name("test").build()
			);	

			given().contentType(ContentType.JSON).queryParam("page", 0).queryParam("size", 10)
				.when().get("/items").then().statusCode(200)
				.body("content", notNullValue())
				.body("content.size()",greaterThanOrEqualTo(0))
				.body("content[0].name", equalTo(itemRequest.getName()))
				.body("content[0].primaryTag", equalTo("POPULAR"));	
		}
	}

	@Nested
	@DisplayName("putRequest")
	class putRequests{
		@Test
		@DisplayName("should check all fields")
		void whenAllField(){
			ItemDataTransferObject savedItem = createItemDto();

			savedItem.setName("test1");
			savedItem.setPrimaryTag(ItemTag.NEW.toString());


			given().contentType(ContentType.JSON).body(savedItem)
				.when().put("/items/" + savedItem.getId()).then().statusCode(200)
				.and()
				.body("name", equalTo("test1"))
				.body("primaryTag", equalTo("NEW"));
		}

		@Test
		@DisplayName("should check tag field")
		void whenUpdateTags(){
			ItemDataTransferObject item = createItemDto();

			item.setPrimaryTag("NEW");

			given().contentType(ContentType.JSON).body(item)
				.when().put("/items/" + item.getId()).then().statusCode(200)
				.and()
				.body("primaryTag", equalTo("NEW"));
		}
	}


	@Test
	void whenDeleteRequestTest(){
		Item savedItem = itemRepository.save(
			Item.builder().name("test").primaryTag(ItemTag.POPULAR).build()
		);	

		when().delete("/items/" + savedItem.getId()).then().statusCode(204);

		assertThrows(NoSuchElementException.class, 
		() -> itemService.findById(savedItem.getId())
		);
	}

	private ItemDataTransferObject createItemDto(){
		return itemMapper.toDto(
			itemRepository.save(Item.builder().description("for cooking").primaryTag(ItemTag.POPULAR).name("test").build())
		);	
	}

}
