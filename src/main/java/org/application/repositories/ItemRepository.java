package org.application.repositories;

import java.util.UUID;

import org.application.entity.Item;
import org.application.enums.ItemTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, UUID> {

	@Query("SELECT i FROM Item i WHERE i.primaryTag = :primaryTag")
	Page<Item> findAllByPrimaryTag(
		@Param("primaryTag") ItemTag primaryTag, Pageable pageable
	);

	@Query("SELECT i FROM Item i WHERE LOWER(i.description) LIKE LOWER(CONCAT('%', :description, '%'))")
	Page<Item> findAllByDescription(
		@Param("description") String description, Pageable pageable
	);

	@Query("SELECT i FROM Item i WHERE " +
		"(:description IS NULL OR LOWER(i.description) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
		"(:primaryTag IS NULL OR LOWER(i.primaryTag) LIKE LOWER(CONCAT('%', :primaryTag, '%')))")
	Page<Item> search(@Param("description") String description, @Param("primaryTag") String primaryTag, Pageable pageable);
}
