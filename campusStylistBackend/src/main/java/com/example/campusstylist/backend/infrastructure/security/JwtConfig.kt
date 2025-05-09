package com.example.campusstylist.backend.infrastructure.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.*

object JwtConfig {
    private const val SECRET = "mySuperSecretKey1234567890abcdef" // Load from env in production
    private const val ISSUER = "http://localhost:8080/"
    private const val AUDIENCE = "campusstylist-api"
    private const val VALIDITY = 24 * 60 * 60 * 1000 // 24 hours in milliseconds

    fun configureJwt(): AuthenticationConfig.() -> Unit = {
        jwt("auth-jwt") {
            verifier(
                JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer(ISSUER)
                    .withAudience(AUDIENCE)
                    .build()
            )
            validate { credential ->
                val email = credential.payload.getClaim("email").asString()
                if (email != null) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }

    fun generateToken(email: String): String {
        return JWT.create()
            .withIssuer(ISSUER)
            .withAudience(AUDIENCE)
            .withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + VALIDITY))
            .sign(Algorithm.HMAC256(SECRET))
    }

    fun verifyToken(token: String): String? {
        return try {
            val verifier = JWT.require(Algorithm.HMAC256(SECRET))
                .withIssuer(ISSUER)
                .withAudience(AUDIENCE)
                .build()
            val decodedJWT = verifier.verify(token)
            decodedJWT.getClaim("email").asString()
        } catch (e: Exception) {
            null
        }
    }
}