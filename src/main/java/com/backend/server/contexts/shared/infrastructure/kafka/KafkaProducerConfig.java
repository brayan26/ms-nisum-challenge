package com.backend.server.contexts.shared.infrastructure.kafka;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.MicrometerProducerListener;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Autowired
    private Environment env;

    @Bean
    public Map<String, Object> producerProps() {
        Map<String, Object>props=new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.server"));
        props.put(ProducerConfig.ACKS_CONFIG, env.getProperty("kafka.producer.acks"));
        props.put(ProducerConfig.RETRIES_CONFIG, env.getProperty("kafka.producer.retries"));
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, env.getProperty("kafka.producer.batch.size"));
        props.put(ProducerConfig.LINGER_MS_CONFIG, env.getProperty("kafka.producer.linger.ms"));
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, env.getProperty("kafka.producer.buffer.memory"));
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, env.getProperty("kafka.producer.max.flight.request"));
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, env.getProperty("kafka.producer.enable.idempotence"));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerProps());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        DefaultKafkaProducerFactory<String, String> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(producerProps());
        kafkaProducerFactory.addListener(new MicrometerProducerListener<>(meterRegistry()));
        return new KafkaTemplate<>(kafkaProducerFactory);
    }

    @Bean
    public MeterRegistry meterRegistry() {
        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    }
}
