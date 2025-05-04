package com.example.study.lab10.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.study.lab10.dto.ItemRequest;
import com.example.study.lab10.model.entity.Item;
import com.example.study.lab10.service.ItemService;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody ItemRequest itemRequestDto) {
        Item response = itemService.createItem(itemRequestDto);
        return ResponseEntity.ok(response);
    }
}
