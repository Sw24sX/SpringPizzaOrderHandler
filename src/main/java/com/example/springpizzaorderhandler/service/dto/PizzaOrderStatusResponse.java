package com.example.springpizzaorderhandler.service.dto;

public record PizzaOrderStatusResponse(Long orderId, OrderStatus status) {
}
