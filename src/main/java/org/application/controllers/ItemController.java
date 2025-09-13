package org.application.controllers;

import java.util.UUID;

import org.application.enums.ItemTag;
import org.application.mapping.ItemDataTransferObject;
import org.application.mapping.ItemMapper;
import org.application.services.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/items")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ItemController {

	ItemService itemService;
	ItemMapper itemMapper;

	@PostMapping
	public Mono<ResponseEntity<ItemDataTransferObject>> save(@Valid @RequestBody ItemDataTransferObject itemDto){
		return Mono.fromSupplier(() -> 
			ResponseEntity.ok(
				itemMapper.toDto(itemService.save(itemMapper.toEntity(itemDto)))
			)
		);
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<ItemDataTransferObject>> findById(@PathVariable("id") UUID id){
		return Mono.fromSupplier(() -> ResponseEntity.ok(itemMapper.toDto(itemService.findById(id))));
	}

	@GetMapping("/tag/{primary_tag}")
	public Mono<ResponseEntity<Page<ItemDataTransferObject>>> findAllByPrimaryTag(
		@PathVariable("primary_tag") String primary_tag,
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "10") int size
	){
		return Mono.fromSupplier(() -> ResponseEntity.ok(
				itemService.findAllByPrimaryTag(
					ItemTag.fromString(primary_tag), PageRequest.of(page, size)
				).map(itemMapper::toDto)
			)
		);
	}

	@GetMapping
	public Mono<ResponseEntity<Page<ItemDataTransferObject>>> getAll(
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "10") int size
	){
		return Mono.fromSupplier(() -> ResponseEntity.ok(
			itemService.getAll(PageRequest.of(page, size)).map(itemMapper::toDto)
		));
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<ItemDataTransferObject>> update(@Valid @RequestBody ItemDataTransferObject itemDto, @PathVariable("id") UUID id){
		return Mono.fromSupplier(() -> ResponseEntity.ok(
			itemMapper.toDto(
				itemService.update(
					itemMapper.toEntity(itemDto), id
				)
			)
		));
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable("id") UUID id){
		itemService.delete(id);
		return Mono.fromSupplier(() ->ResponseEntity.noContent().build()); //204 
	}

}
