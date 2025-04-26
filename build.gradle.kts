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
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
