plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "com.productivitytracker"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
}

application {
    mainClass.set("com.productivitytracker.AppKt")
}

kotlin {
    jvmToolchain(21)
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.productivitytracker.AppKt"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

// Optimize Gradle performance
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "21"
        freeCompilerArgs = listOf("-Xjsr305=strict", "-progressive")
        allWarningsAsErrors = false
        suppressWarnings = true
    }
}

// Speed up incremental builds
tasks.withType<JavaCompile> {
    options.isIncremental = true
}
