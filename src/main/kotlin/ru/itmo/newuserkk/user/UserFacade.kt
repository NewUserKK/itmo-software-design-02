package ru.itmo.newuserkk.user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserFacade(private val userStorage: UserStorage) {
    suspend fun registerUser(user: User): Unit = withContext(Dispatchers.IO) {
        userStorage.saveUser(user)
    }

    suspend fun getUserById(id: Long): User? = withContext(Dispatchers.IO) {
        userStorage.getUserById(id)
    }
}
