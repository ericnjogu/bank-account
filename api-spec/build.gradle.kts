plugins {
    java
    id("org.openapi.generator")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

val apiSpecOutputDir = "${buildDir}/generated/api.spec"
tasks {
    register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generate-api-spec") {
        mustRunAfter(processResources)
        globalProperties.set(
            mapOf(
                "apiDocs" to "false",
                "modelDocs" to "false"
            )
        )
        generatorName.set("spring")
        inputSpec.set("$buildDir/resources/main/api.yaml")
        apiPackage.set("com.enjogu.bank.account.api")
        outputDir.set(apiSpecOutputDir)
        modelPackage.set("com.enjogu.bank.account.models")
        //invokerPackage.set("com.enjogu.bank.account.infrastructure")
        generateModelDocumentation.set(false)
        generateApiDocumentation.set(false)
        configOptions.set(
            mapOf(
                "useBeanValidation" to "true",
                "serializationLibrary" to "jackson",
                "interfaceOnly" to "true",
                "unhandledException" to "true"
            )
        )
    }

    compileJava {
        dependsOn("generate-api-spec")
    }

    bootJar {
        isEnabled = false
    }

    jar {
        enabled = true
    }
}

sourceSets {
    main {
        java {
            srcDir("$apiSpecOutputDir/src/main/java")
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.springfox:springfox-swagger2:2.4.0")
    implementation("io.springfox:springfox-spring-web:2.4.0")
    implementation("org.hibernate.validator:hibernate-validator")
    implementation("org.openapitools:jackson-databind-nullable:0.2.1")
}
