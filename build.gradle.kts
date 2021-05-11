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
    implementation(project(":api-spec"))

    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.projectlombok:lombok:1.18.20")
    implementation("org.hibernate.validator:hibernate-validator")
    implementation("org.liquibase:liquibase-core")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    annotationProcessor("org.projectlombok:lombok:1.18.20")

    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    testAnnotationProcessor("org.projectlombok:lombok:1.18.20")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}