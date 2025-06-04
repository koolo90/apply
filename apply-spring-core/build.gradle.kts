plugins {
  java
  war
  id("org.springframework.boot") version "3.5.0"
  id("io.spring.dependency-management") version "1.1.7"
}

group = "com.brocode"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(23)
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

dependencies {
  //implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  //implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.kafka:spring-kafka")
  //implementation("org.flywaydb:flyway-core")
  //implementation("org.flywaydb:flyway-database-postgresql")
  compileOnly("org.projectlombok:lombok")
  //runtimeOnly("org.postgresql:postgresql")
  annotationProcessor("org.projectlombok:lombok")
  providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.kafka:spring-kafka-test")
  testImplementation("org.springframework.security:spring-security-test")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
  useJUnitPlatform()
}
