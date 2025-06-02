plugins {
    java
}

group = "com.brocode.apply"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("com.h2database:h2:2.3.232")
    runtimeOnly("org.postgresql:postgresql:42.7.2")
}