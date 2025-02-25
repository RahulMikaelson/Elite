package com.elite.service.order.mapper;

import com.elite.service.order.dto.OrderDetailsDTO;
import com.elite.service.order.dto.OrderRequestDTO;
import com.elite.service.order.dto.OrderResponseDTO;
import com.elite.service.order.dto.OrderStatusDTO;
import com.elite.service.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderDetailsDTO orderToOrderDetailsDTO(Order order);

    Order OrderRequestDTOToOrder(OrderRequestDTO orderRequestDTO);


    OrderResponseDTO orderToOrderResponseDTO(Order order);

    OrderStatusDTO orderToOrderStatusDTO(Order order);
}
