package com.example.saferoad.utils.kafka

suspend fun performLogin(login: String, password: String): Boolean {
    val responseTopic = "login_response_${System.currentTimeMillis()}"

    val requestJson = """
        {
            "username": "$login",
            "password": "$password",
            "response_topic": "$responseTopic"
        }
    """.trimIndent()

    MyKafkaProducer.send("login_requests", requestJson)

    val response = MyKafkaConsumer.listenForResponse(responseTopic)
    return response
}