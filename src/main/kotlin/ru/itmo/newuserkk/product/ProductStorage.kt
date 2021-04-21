package ru.itmo.newuserkk.product

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import javax.money.CurrencyUnit

class ProductStorage {
    suspend fun addProduct(product: Product) = newSuspendedTransaction {
        ProductEntity.new {
            name = product.name
        }
    }

    suspend fun getProductList(currencyUnit: CurrencyUnit): List<ProductWithPrice> =
        newSuspendedTransaction {
            val products = ProductEntity.all()
            val prices = products.map {
                val product = it.toProduct()
                getPriceForProduct(product.id, currencyUnit)
            }

            products.zip(prices).mapNotNull { (product, price) ->
                when {
                    price != null -> ProductWithPrice(product.toProduct(), price)
                    else -> null
                }
            }
        }


    suspend fun getPriceForProduct(productId: Long, currencyUnit: CurrencyUnit): Price? =
        newSuspendedTransaction {
            PriceEntity
                .find {
                    (PriceTable.amount.currency eq currencyUnit) and
                        (PriceTable.productId eq productId)
                }
                .firstOrNull()
                ?.toPrice()
        }
}
