package ru.itmo.newuserkk.application

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import ru.itmo.newuserkk.product.DEFAULT_CURRENCY
import ru.itmo.newuserkk.product.Product
import ru.itmo.newuserkk.product.ProductFacade
import ru.itmo.newuserkk.product.ProductStorage
import ru.itmo.newuserkk.user.User
import ru.itmo.newuserkk.user.UserFacade
import ru.itmo.newuserkk.user.UserStorage
import javax.money.Monetary

fun Application.configureRouting() {
    val userFacade = UserFacade(UserStorage())
    val productFacade = ProductFacade(ProductStorage())

    routing {
        route("/api") {
            route("/user") {
                get("{id}") {
                    val id = call.parameters.getOrFail("id").toLong()
                    val user = userFacade.getUserById(id)
                    call.respond(HttpStatusCode.OK, user ?: "null")
                }

                post("/register") {
                    val user = call.receive<User>()
                    userFacade.registerUser(user)
                    call.respond(HttpStatusCode.OK)
                }
            }

            route("/product") {
                get("") {
                    val userId = call.request.queryParameters["userId"]
                    val currency = when {
                        userId != null -> {
                            val user = userFacade.getUserById(userId.toLong())
                            when {
                                user != null -> user.desiredCurrency
                                else -> DEFAULT_CURRENCY
                            }
                        }
                        else -> DEFAULT_CURRENCY
                    }

                    call.respond(HttpStatusCode.OK, productFacade.getProductList(currency))
                }

                post("") {
                    val product = call.receive<Product>()
                    productFacade.addProduct(product)
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}
