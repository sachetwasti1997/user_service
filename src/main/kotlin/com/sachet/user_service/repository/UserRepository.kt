package com.sachet.user_service.repository

import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Repository


@Repository
class UserRepository(
    @Autowired
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {
    suspend fun findUserByUserName(userName: String): User?{
        val query = Query(Criteria.where("userName").`is`(userName))
        return reactiveMongoTemplate.find(query, User::class.java).awaitFirstOrNull()
    }
}