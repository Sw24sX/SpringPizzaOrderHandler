package com.example.springpizzaorderhandler.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.example.springpizzaorderhandler.common.Profile.HOMEWORK;

@Configuration
@Profile(HOMEWORK)
public class HomeworkConfig {

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Declarables pizzaOrderDeclarables() {

        var homeworkRq = new Queue("homework.rq", false);
        var homeworkRs = new Queue("homework.rs", false);
        var homeworkExchange = new DirectExchange("homework");
        return new Declarables(
                homeworkRq,
                homeworkRs,
                homeworkExchange,
                BindingBuilder.bind(homeworkRq).to(homeworkExchange).with("homework.in"),
                BindingBuilder.bind(homeworkRs).to(homeworkExchange).with("homework.out")
        );
    }
}
