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
