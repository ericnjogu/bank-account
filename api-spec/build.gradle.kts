plugins {
    java
    id("org.openapi.generator")
}

tasks {
    register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generate-api-spec") {
        mustRunAfter(processResources)

        generatorName.set("spring")
        inputSpec.set("$buildDir/resources/main/api.yaml")
        apiPackage.set("com.enjogu.bank.account.api")
        outputDir.set("${buildDir}/generated/api.spec")
        modelPackage.set("com.enjogu.bank.account.models")
        invokerPackage.set("com.enjogu.bank.account.infrastructure")
    }

    compileJava {
        dependsOn("generate-api-spec")
    }
}
