plugins {
    java
    war
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.brocode.apply"
version = "0.0.1-SNAPSHOT" //https://semver.org/

dependencies {
    /* Here are our Spring Boot project’s dependencies: */
    implementation("org.springframework.boot:spring-boot-starter-web:3.5.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.0")
    implementation("org.springframework.boot:spring-boot-starter-security:3.5.0")
    implementation("org.flywaydb:flyway-core")
    compileOnly("org.projectlombok:lombok:1.18.38")

    annotationProcessor("org.projectlombok:lombok:1.18.38")
    runtimeOnly("com.h2database:h2:2.3.232")
    runtimeOnly("org.postgresql:postgresql:42.7.2")

    testCompileOnly("org.projectlombok:lombok:1.18.38")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.38")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
