package ru.itmo.newuserkk.product

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.money.compositeMoney
import java.math.BigDecimal
import java.util.*
import javax.money.CurrencyUnit
import javax.money.Monetary
import javax.money.MonetaryAmount

val DEFAULT_CURRENCY: CurrencyUnit = Monetary.getCurrency("RUB")

data class Product(
    val name: String,
    val id: Long = 0,
)

object ProductTable : LongIdTable("products") {
    val name = varchar("name", 200)
}

class ProductEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ProductEntity>(ProductTable)

    var name by ProductTable.name

    fun toProduct(): Product = Product(
        name = name,
        id = id.value
    )
}


data class Price(
    val productId: Long,
    val amount: MonetaryAmount
)

object PriceTable : LongIdTable("prices") {
    val productId = reference("product_id", PriceTable.id)
    val amount = compositeMoney(2, 2, "amount", "currency")
}

class PriceEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PriceEntity>(PriceTable)

    var productId by PriceTable.productId
    var amount: MonetaryAmount by PriceTable.amount

    fun toPrice(): Price = Price(
        productId = productId.value,
        amount = amount
    )
}


data class ProductWithPrice(
    val product: Product,
    val price: Price
)
