package ru.itmo.newuserkk.product

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.money.CurrencyUnit

class ProductFacade(private val productStorage: ProductStorage) {
    suspend fun addProduct(product: Product): Unit = withContext(Dispatchers.IO) {
        productStorage.addProduct(product)
    }

    suspend fun getProductList(currencyUnit: CurrencyUnit) = withContext(Dispatchers.IO) {
        productStorage.getProductList(currencyUnit)
    }
}
