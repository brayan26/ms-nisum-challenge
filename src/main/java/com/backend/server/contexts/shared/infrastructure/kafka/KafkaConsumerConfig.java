package com.backend.server.contexts.shared.infrastructure.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Autowired
    private Environment env;

    @Bean
    public Map<String, Object> consumerProps() { Map<String, Object>props=new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.server"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("kafka.consumer.groupId"));
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, env.getProperty("kafka.consumer.autocommit.interval.ms"));
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, env.getProperty("kafka.consumer.session.timeout"));
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, env.getProperty("kafka.consumer.max.poll.interval.ms"));
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, env.getProperty("kafka.consumer.max.poll.records"));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps());
    }

    @Bean(name = "listenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        listenerContainerFactory.setConsumerFactory(consumerFactory());
        //Read Batch message
        listenerContainerFactory.setBatchListener(true);
        //Threads to consume messages
        //value defined by the number of partitions in the topic
        listenerContainerFactory.setConcurrency(5);
        return listenerContainerFactory;
    }
}
