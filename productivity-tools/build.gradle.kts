plugins {
    kotlin("jvm") version "1.3.72"
    application
}

group = "com.productivitytracker"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.72")
    implementation("org.xerial:sqlite-jdbc:3.30.1")
}

application {
    mainClassName = "com.productivitytracker.AppKt"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
