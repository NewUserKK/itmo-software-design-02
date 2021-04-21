package ru.itmo.newuserkk.application

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import javax.money.CurrencyUnit
import javax.money.Monetary

class CurrencyUnitSerializer : JsonSerializer<CurrencyUnit>() {
    override fun serialize(value: CurrencyUnit, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(value.currencyCode)
    }
}

class CurrencyUnitDeserializer : JsonDeserializer<CurrencyUnit>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): CurrencyUnit =
        Monetary.getCurrency(p.codec.readTree<JsonNode>(p).textValue())
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            registerKotlinModule()
            registerModule(SimpleModule().apply {
                addSerializer(CurrencyUnit::class.java, CurrencyUnitSerializer())
                addDeserializer(CurrencyUnit::class.java, CurrencyUnitDeserializer())
            })
        }
    }
}
