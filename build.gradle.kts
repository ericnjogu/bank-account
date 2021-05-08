plugins {
    java
    kotlin("jvm") version "1.4.32"
    id("org.openapi.generator")  version "5.1.1" apply false
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

allprojects {
    group = "com.enjogu"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":api-spec"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}