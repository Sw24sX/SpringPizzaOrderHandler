package com.example.springpizzaorderhandler.service;

import com.example.springpizzaorderhandler.service.dto.homework.HomeworkEvent;
import com.example.springpizzaorderhandler.service.dto.homework.HomeworkResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class HomeworkEventListener {

    RabbitTemplate rabbitTemplate;
    private static final String HOMEWORK_EXCHANGE = "homework";
    private static final String HOMEWORK_ROUTING_KEY = "homework.out";

    @SneakyThrows
    @EventListener
    @Async
    public void homeworkEventHandle(HomeworkEvent homeworkEvent) {
        for (var i = 0; i < homeworkEvent.count(); i++) {

            log.info("Send message {} with text {} to {} and routing key {}",
                    i, homeworkEvent.text(), HOMEWORK_EXCHANGE, HOMEWORK_ROUTING_KEY);
            rabbitTemplate.convertAndSend(HOMEWORK_EXCHANGE, HOMEWORK_ROUTING_KEY, new HomeworkResponse(i, homeworkEvent.text()));
            Thread.sleep(homeworkEvent.delay());
        }
    }
}
