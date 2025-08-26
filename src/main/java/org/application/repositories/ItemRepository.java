package org.application.repositories;

import java.util.UUID;

import org.application.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, UUID> {}
