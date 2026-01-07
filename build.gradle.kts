plugins {
    kotlin("jvm") version "2.2.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.formdev:flatlaf:3.4")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.apache.poi:poi-ooxml:5.2.5")
    implementation("com.github.librepdf:openpdf:1.3.30")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}