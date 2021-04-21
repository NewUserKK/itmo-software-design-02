package ru.itmo.newuserkk.user

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import java.util.*
import javax.money.CurrencyUnit
import javax.money.Monetary
import kotlin.math.log

data class User(
    val login: String,
    val desiredCurrency: CurrencyUnit,
    val id: Long = 0
)

object UserTable : LongIdTable("user") {
    val login = varchar("login", 60).uniqueIndex()
    val desiredCurrency = varchar("desired_currency", 30)
}

class UserEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserEntity>(UserTable)

    var login by UserTable.login
    var desiredCurrency by UserTable.desiredCurrency

    fun toUser(): User = User(
        login = login,
        desiredCurrency = Monetary.getCurrency(desiredCurrency),
        id = id.value
    )
}
