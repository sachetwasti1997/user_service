package com.sachet.user_service.security

import com.sachet.user_service.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.function.Function
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JsonWebTokenUtility {

    private val SECRET =
        "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzYWNoZXRfd2FzdGkxIiwiaWF0IjoxNjUxNTY2MjU5LCJleHAiOjE2NTE2MDIyNTl9.g1-bbXatc8ZXliQQkvHOHwHHt-9ny_Zbf-fcfSp4DLIYvr8EL4-H-7VrUyAbUb9H$2a$10RC2UTyJmSgT0gzWLJc39auH6flxqF1o1PZpaMJxq1RVnsnYSPXDw2"

    fun isTokenExpired(token: String): Boolean{
        val exp = extractExpiration(token).before(Date())
        if (exp){
            throw Exception("Expired Jwt")
        }
        return false
    }

    fun extractUsername(token: String): String {
        return extractClaims(token) { obj: Claims -> obj.subject }
    }

    fun extractExpiration(token: String): Date{
        return extractClaims(token){obj: Claims -> obj.expiration}
    }

    fun <T> extractClaims(token: String, claimsResolver: Function<Claims, T>): T{
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        val secretKey = Keys.hmacShaKeyFor(SECRET.toByteArray())
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun generateToken(user: User): String{
        val claims = HashMap<String, Any?>()
        return createToken(claims, user)
    }

    private fun createToken(claims: Map<String, Any?>, user: User): String{
        val key = Keys.hmacShaKeyFor(SECRET.toByteArray())
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.userName)
            .claim("roles", user.roles)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(key).compact()
    }

    fun validateToken(token: String, user: User): Boolean{
        val userName = extractUsername(token)
        return userName == user.userName && !isTokenExpired(token)
    }

}