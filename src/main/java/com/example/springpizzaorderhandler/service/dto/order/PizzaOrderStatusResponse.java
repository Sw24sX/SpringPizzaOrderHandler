package com.example.springpizzaorderhandler.service.dto.order;

public record PizzaOrderStatusResponse(Long orderId, OrderStatus status) {
}
