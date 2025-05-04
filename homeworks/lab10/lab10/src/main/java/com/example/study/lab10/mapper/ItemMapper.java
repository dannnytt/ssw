package com.example.study.lab10.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.study.lab10.dto.ItemRequest;
import com.example.study.lab10.model.entity.Item;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mapping(target = "shippingWeight.measurement.name", source = "shippingWeight.name")
    @Mapping(target = "shippingWeight.measurement.symbol", source = "shippingWeight.symbol")
    Item toEntity(ItemRequest itemDto);
}
