package com.example.saferoad.utils.kafka

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

object MyKafkaConsumer {
    suspend fun listenForResponse(topic: String): Boolean {
        val props = Properties()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092" // Adjust to your Kafka broker
        props[ConsumerConfig.GROUP_ID_CONFIG] = "login-consumer-group"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"

        val consumer = KafkaConsumer<String, String>(props)
        consumer.subscribe(listOf(topic))

        return withContext(Dispatchers.IO) {
            try {
                val records = consumer.poll(Duration.ofSeconds(10))
                for (record in records) {
                    if (record.value().contains("\"status\":\"success\"")) {
                        return@withContext true
                    }
                }
                false
            } finally {
                consumer.close()
            }
        }
    }
}