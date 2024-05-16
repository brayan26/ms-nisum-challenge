package com.backend.server.contexts.users.infrastructure.consumers;

import com.backend.server.contexts.users.infrastructure.utils.ConsumersList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserCreatedConsumer {

    @KafkaListener(topics = ConsumersList.USER_CREATED_EVENT,
            containerFactory = "listenerContainerFactory",
            groupId = ConsumersList.CONSUMER_GROUP_ID)
    public void on(List<String> messages) {
        long init = System.currentTimeMillis();

        log.info("Staring message reading");
        for (String message: messages) {
            //TODO: transform object received and call your use cases
            log.info("Message received from kafka => {}", message);
        }

        log.info("Bach completed {}ms", (System.currentTimeMillis() - init));
    }

}
