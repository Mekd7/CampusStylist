plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("application")
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
    implementation("io.ktor:ktor-server-core:2.3.5")
    implementation("io.ktor:ktor-server-netty:2.3.5")
    implementation("io.ktor:ktor-server-auth:2.3.5")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.5")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")

    // PostgreSQL driver
    implementation("org.postgresql:postgresql:42.7.3")

    // Exposed (Kotlin SQL library)
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.11")

    // Testing
    testImplementation("io.ktor:ktor-server-tests:2.3.5")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
}

application {
    mainClass.set("com.example.campusstylist.backend.MainKt")
}