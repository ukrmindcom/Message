plugins {
    kotlin("jvm") version "1.9.22"
    application
}

allprojects {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

tasks.test {
    useJUnitPlatform()
}