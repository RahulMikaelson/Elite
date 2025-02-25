package com.elite.service.order.dto;

import com.elite.service.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusDTO {

    private OrderStatus status;
}
