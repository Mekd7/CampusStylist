plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    id("application")
    id("java")
    alias(libs.plugins.ktor)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    // Ktor dependencies
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // JWT token generation
    implementation(libs.java.jwt)

    // PostgreSQL driver
    implementation(libs.postgresql)

    // Exposed (Kotlin SQL library)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)

    // Logging
    implementation(libs.logback.classic)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Testing
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotest.runner.junit5)
}

application {
    mainClass.set("com.example.campusstylist.backend.MainKt")
}