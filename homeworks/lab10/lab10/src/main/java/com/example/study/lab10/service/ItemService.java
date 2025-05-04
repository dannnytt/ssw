package com.example.study.lab10.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.study.lab10.dto.ItemRequest;
import com.example.study.lab10.mapper.ItemMapper;
import com.example.study.lab10.model.entity.Item;
import com.example.study.lab10.repository.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    public Item createItem(ItemRequest itemDto) {
        Item item = itemMapper.toEntity(itemDto);
        return itemRepository.save(item);
    }

}
