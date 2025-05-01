package com.example.campusstylist.backend.infrastructure.security


import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtConfig {
    private const val SECRET = "your-secret-key" // In production, use environment variable
    private const val ISSUER = "campusstylist"
    private const val VALIDITY_MS = 36_000_00 * 24 // 24 hours

    private val algorithm = Algorithm.HMAC256(SECRET)

    fun generateToken(userId: Long, role: String): String = JWT.create()
        .withSubject(userId.toString())
        .withIssuer(ISSUER)
        .withClaim("role", role)
        .withExpiresAt(Date(System.currentTimeMillis() + VALIDITY_MS))
        .sign(algorithm)

    fun verifier(): JWTVerifier = JWT.require(algorithm)
        .withIssuer(ISSUER)
        .build()
}