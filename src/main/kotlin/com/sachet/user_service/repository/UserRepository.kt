package com.sachet.user_service.repository

import com.sachet.user_service.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    @Autowired
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {

    suspend fun saveUser(user: User): User?{
        return reactiveMongoTemplate.save(user).awaitFirstOrNull()
    }

    suspend fun findUserByUserName(userName: String?): User?{
        val query = Query(Criteria.where("userName").`is`(userName))
        return reactiveMongoTemplate.find(query, User::class.java).awaitFirstOrNull()
    }

    fun findAllUser(userId: String, page: Pageable): Flow<User>{
        val query = Query(Criteria.where("id").ne(userId))
        query.with(page)
        return reactiveMongoTemplate.find(query, User::class.java).asFlow()
    }

}