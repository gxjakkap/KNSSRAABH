val databaseUrl: String by project

plugins {
    id("java")
    id("application")
}

group = "me.guntxjakka"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("ModNav.Main")
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}

tasks.register("getData") {
    doLast {
        val outputFile = file("./data.db")
        outputFile.parentFile.mkdirs()

        try {
            ant.withGroovyBuilder {
                "get"(
                    "src" to databaseUrl,
                    "dest" to outputFile,
                    "skipexisting" to true
                )
            }
            println("Database downloaded to: ${outputFile.absolutePath}")
        } catch (e: Exception) {
            throw GradleException("Failed to download database: ${e.message}", e)
        }

        // Verify file was downloaded and is not empty
        if (!outputFile.exists() || outputFile.length() == 0L) {
            throw GradleException("Database file download failed or file is empty")
        }
    }
}
