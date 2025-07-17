import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    application
}

group = "message"
version = "2.0.0"

application {
        mainClass.set("message.Main")
}

sourceSets {
    named("main") {
        kotlin {
            srcDirs("main/src")
        }
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Discord API
    implementation("dev.kord:kord-core:0.13.0")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.13")
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.9")

    // Environment Variables
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")

    // Asynchronous Operations
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    // Testing
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "message.Main")
    }
}


