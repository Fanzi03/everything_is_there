package org.application.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.application.BaseIntegrationTest;
import org.application.entity.Item;
import org.application.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) 
@Testcontainers
public class ItemControllerTest extends BaseIntegrationTest{

	@Autowired
	ItemRepository itemRepository;

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
		Item itemRequest = new Item();
		itemRequest.setName("test item");
		Item actualItem = given().contentType(ContentType.JSON).body(itemRequest)
			.when().post("/items").then().statusCode(200).extract().as(Item.class);

		assertThat(actualItem.getName()).isEqualTo(itemRequest.getName());
	}

	@Test
	void whenGetRequestTest(){
		Item itemRequest = new Item();
		itemRequest.setName("test item");

		Item savedItem = itemRepository.save(itemRequest);	
		when().get("/items/" + savedItem.getId()).then().statusCode(200)
			.and().body("name", equalTo("test item")).body("id", notNullValue());	
	}


	@Test
	void whenPutRequetsTest(){
		Item savedItem = itemRepository.save(Item.builder().name("test").build());	
		savedItem.setName("test1");

		given().contentType(ContentType.JSON).body(savedItem)
			.when().put("/items/" + savedItem.getId()).then().statusCode(200)
			.and().body("name", equalTo("test1"));
	}

	@Test
	void whenDeleteRequestTest(){
		Item savedItem = itemRepository.save(Item.builder().name("test").build());	
		
		when().delete("/items/" + savedItem.getId()).then().statusCode(200);
	}

}
