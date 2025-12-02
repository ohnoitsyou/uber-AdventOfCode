import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.spring") version "2.2.20"
    kotlin("plugin.allopen") version "2.2.20"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.13"
    application
}

application {
    mainClass = "dev.dayoung.adventofcode.UAoCApplicationKt"
}

// Configure all-open to automatically 'open' any class with @State
allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}

group = "dev.dayoung.adventofcode"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
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

    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.13")
}
sourceSets {
    create("benchmark")
}

kotlin {
    target {
        compilations.getByName("benchmark").associateWith(compilations.getByName("main"))
    }
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

benchmark {
    targets {
        register("benchmark")
    }
    configurations {
        register("day202502") {
            include("Day202502Benchmark")
        }
    }
}
