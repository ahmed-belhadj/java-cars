package com.cars.restcars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogConsumer
{
    @RabbitListener(queues = RestcarsApplication.QUEUE_NAME)
    public void consumeMessage(final Message message)
    {
        log.info("Received Message: {}", message.toString());
    }
}