package ru.itmo.newuserkk.application

import io.ktor.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ru.itmo.newuserkk.product.PriceTable
import ru.itmo.newuserkk.product.ProductTable
import ru.itmo.newuserkk.user.UserTable

fun Application.configureDatabase() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/sd_reactive",
        driver = "org.postgresql.Driver",
        user = "newuserkk",
        password = "AST1287b_"
    )

    transaction {
        SchemaUtils.create(
            ProductTable,
            PriceTable,
            UserTable
        )
    }
}
