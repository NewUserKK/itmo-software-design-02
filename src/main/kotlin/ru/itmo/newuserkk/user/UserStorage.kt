package ru.itmo.newuserkk.user

import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserStorage {
    suspend fun saveUser(user: User) = newSuspendedTransaction {
        UserEntity.new {
            login = user.login
            desiredCurrency = user.desiredCurrency.currencyCode
        }
    }

    suspend fun getUserById(id: Long): User? = newSuspendedTransaction {
        UserEntity.findById(id)?.toUser()
    }
}