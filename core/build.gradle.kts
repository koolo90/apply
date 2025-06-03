plugins {
    java
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("ear")
}

// Configure the ear task
tasks.ear {
    archiveFileName.set("apply-core.ear")
    deploymentDescriptor {
        applicationName = "apply-core"
        displayName = "Apply Core Application"
        description = "Apply Core Enterprise Application"
        initializeInOrder = true
        libraryDirectory = "APP-INF/lib"
    }
}

group = "com.brocode.apply"
version = "0.0.1-SNAPSHOT" //https://semver.org/

dependencies {
    /* Here are our Spring Boot project’s dependencies: */
    implementation(project(":database"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.5.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.0")
    implementation("org.springframework.boot:spring-boot-starter-security:3.5.0")
    // Removed Flyway dependency as it's not needed in core project
    // implementation("org.flywaydb:flyway-core")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    compileOnly("org.projectlombok:lombok:1.18.38")

    // Java EE API for EAR deployment
    compileOnly("jakarta.platform:jakarta.jakartaee-api:9.1.0")

    // For EAR packaging
    earlib("org.springframework.boot:spring-boot-starter-web:3.5.0")
    earlib("org.springframework.boot:spring-boot-starter-data-jpa:3.5.0")
    earlib("org.springframework.boot:spring-boot-starter-security:3.5.0")

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
