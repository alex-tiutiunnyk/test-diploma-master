package com.example.saferoad.utils.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class KafkaProducer {
    private val producer = KafkaProducer<String, String>(Properties().apply {
        setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092") // Replace with your Kafka broker address
        setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.canonicalName)
        setProperty(
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            StringSerializer::class.java.canonicalName
        )
    })

    fun sendMessage(topic: String, key: String, value: String) {
        val record = ProducerRecord(topic, key, value)
        producer.send(record)
    }
}