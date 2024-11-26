package com.example.saferoad.utils.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*

object MyKafkaProducer {
    private val producer: KafkaProducer<String, String>

    init {
        val props = Properties()
        props["bootstrap.servers"] = "localhost:9092" // Adjust to your Kafka broker
        props["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        props["value.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        producer = KafkaProducer(props)
    }

    fun send(topic: String, message: String) {
        val record = ProducerRecord<String, String>(topic, message)
        producer.send(record) { metadata, exception ->
            if (exception != null) {
                println("Error sending message: ${exception.message}")
            } else {
                println("Message sent to topic ${metadata.topic()}, partition ${metadata.partition()}, offset ${metadata.offset()}")
            }
        }
    }
}