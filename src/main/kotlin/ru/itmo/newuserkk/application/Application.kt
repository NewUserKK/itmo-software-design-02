package ru.itmo.newuserkk.application

import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.itmo.newuserkk.application.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
        configureDatabase()
    }.start(wait = true)
}
