package com.example.springpizzaorderhandler.service;

import com.example.springpizzaorderhandler.service.dto.order.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static com.example.springpizzaorderhandler.common.Profile.PIZZA;

@Log4j2
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Profile(PIZZA)
public class PizzaOrderHandler {

    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "pizza.order.create.rq")
    public void pizzaOrderRequestListener(PizzaOrderRequest pizzaOrderRequest) {
        log.info("Receive: {}", pizzaOrderRequest);
        for (var e : OrderStatus.values()) {
            sendResponseStatus(pizzaOrderRequest.orderId(), e);
        }
    }

    private void sendResponseStatus(Long orderId, OrderStatus status) {
        var response = new PizzaOrderStatusResponse(orderId, status);
        rabbitTemplate.convertAndSend("pizza.order", "order.create.rs", response);
    }

    @RabbitListener(queues = "pizza.order.cancel.rq")
    public PizzaOrderCancelResponse pizzaOrderCancelListener(PizzaOrderCancelRequest request) {
        log.info("Receive: {}", request);
        return new PizzaOrderCancelResponse(true, "Success");
    }

}
