import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.spring") version "2.1.0"
    application
}

application {
    mainClass = "dev.dayoung.adventofcode.UAoCApplicationKt"
}


group = "dev.dayoung.adventofcode"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.github.ajalt.clikt:clikt:4.2.1")

    implementation("org.apache.commons:commons-lang3:3.13.0")
    implementation("org.apache.commons:commons-text:1.11.0")

    implementation("io.github.oshai:kotlin-logging:7.0.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
