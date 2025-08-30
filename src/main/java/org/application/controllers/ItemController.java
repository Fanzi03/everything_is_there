package org.application.controllers;

import java.util.UUID;

import org.application.entity.Item;
import org.application.services.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/items")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ItemController {

	ItemService itemService;

	@PostMapping
	public ResponseEntity<Item> save(@RequestBody Item item){
		return ResponseEntity.ok(itemService.save(item));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Item> findById(@PathVariable("id") UUID id){
		return ResponseEntity.ok(itemService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Item> update(@RequestBody Item item, @PathVariable("id") UUID id){
		return ResponseEntity.ok(itemService.update(item, id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Item> delete(@PathVariable("id") UUID id){
		Item delItem = itemService.findById(id);
		itemService.delete(id);
		return ResponseEntity.ok(delItem);
	}
	
}
