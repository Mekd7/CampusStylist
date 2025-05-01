package com.example.campusstylist.backend.infrastructure.security



import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun AuthenticationConfig.configureJwt() {
    jwt("jwt") {
        verifier(JwtConfig.verifier())
        validate { credential ->
            if (credential.payload.getClaim("role") != null) {
                JWTPrincipal(credential.payload)
            } else {
                null
            }
        }
    }
}