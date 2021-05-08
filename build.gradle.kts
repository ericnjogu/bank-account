plugins {
    java
    kotlin("jvm") version "1.4.32"
    id("org.openapi.generator")  version "5.1.1" apply false
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
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}