package com.sibsutis.study.lab7.repository;

import com.sibsutis.study.lab7.domain.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
