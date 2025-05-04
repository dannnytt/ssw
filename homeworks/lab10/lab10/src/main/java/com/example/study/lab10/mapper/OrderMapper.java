package com.example.study.lab10.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.study.lab10.dto.OrderDetailDto;
import com.example.study.lab10.dto.OrderRequest;
import com.example.study.lab10.dto.OrderResponse;
import com.example.study.lab10.model.entity.Order;
import com.example.study.lab10.model.entity.OrderDetail;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "orderDetails", source = "orderDetails")
    Order toEntity(OrderRequest dto);

    @Mapping(target = "item.id", source = "itemId")
    @Mapping(target = "quantity.measurement.name", source = "quantity.name")
    @Mapping(target = "quantity.measurement.symbol", source = "quantity.symbol")
    OrderDetail toEntity(OrderDetailDto dto);

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "quantity.measurement.name", target = "quantity.name")
    @Mapping(source = "quantity.measurement.symbol", target = "quantity.symbol")
    OrderDetailDto toDto(OrderDetail detail);

    @Mapping(source = "customer.id", target = "userId")
    @Mapping(source = "payment.id", target = "paymentId")
    OrderResponse toDto(Order order);
}
