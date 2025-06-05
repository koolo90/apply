plugins {
    java
    war
}

group = "com.brocode"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation(platform("jakarta.platform:jakarta.jakartaee-bom:10.0.0"))
    implementation("jakarta.platform:jakarta.jakartaee-api:10.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<War>("war") {
    archiveFileName.set("apply-jakarta.war")
}