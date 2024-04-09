package com.example.springpizzaorderhandler.service;

import com.example.springpizzaorderhandler.service.dto.homework.HomeworkEvent;
import com.example.springpizzaorderhandler.service.dto.homework.HomeworkRequest;
import com.example.springpizzaorderhandler.service.dto.homework.HomeworkResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.springpizzaorderhandler.common.Profile.HOMEWORK;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@Profile(HOMEWORK)
public class HomeworkHandler {

    ApplicationEventPublisher applicationEventPublisher;

    @RabbitListener(queues = "homework.rq")
    public void handle(HomeworkRequest request) {

        log.info("homework message received");

        //Чтобы не тормозить обработку следующих сообщений
        applicationEventPublisher.publishEvent(new HomeworkEvent(request.count(), request.text(), request.delay()));
    }
}
