plugins {
    java
    id("org.flywaydb.flyway") version "10.8.1"
}

group = "com.brocode.apply"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.flywaydb:flyway-core:10.8.1")
    runtimeOnly("com.h2database:h2:2.3.232")
    runtimeOnly("org.postgresql:postgresql:42.7.2")
}

flyway {
    url = System.getenv("SPRING_DATASOURCE_URL") ?: "jdbc:postgresql://localhost:5432/applydb"
    user = System.getenv("POSTGRES_USER") ?: "postgres"
    password = System.getenv("POSTGRES_PASSWORD") ?: "postgres"
    locations = arrayOf("filesystem:src/main/resources/db/migration")
    baselineOnMigrate = true
}

tasks.register("migrateDatabase") {
    group = "database"
    description = "Runs Flyway migrations against the database container"
    dependsOn("flywayMigrate")

    doFirst {
        val dbUrl = System.getenv("SPRING_DATASOURCE_URL") ?: "jdbc:postgresql://localhost:5432/applydb"
        val dbUser = System.getenv("POSTGRES_USER") ?: "postgres"

        println("Running database migrations...")
        println("Using database URL: $dbUrl")
        println("Using database user: $dbUser")
    }

    doLast {
        println("Database migrations completed successfully!")
    }
}

// Add a task to display the current Flyway configuration
tasks.register("showFlywayConfig") {
    group = "database"
    description = "Displays the current Flyway configuration"

    doLast {
        val dbUrl = System.getenv("SPRING_DATASOURCE_URL") ?: "jdbc:postgresql://localhost:5432/applydb"
        val dbUser = System.getenv("POSTGRES_USER") ?: "postgres"
        val locations = "filesystem:src/main/resources/db/migration"

        println("Flyway Configuration:")
        println("URL: $dbUrl")
        println("User: $dbUser")
        println("Locations: $locations")
        println("Baseline on Migrate: true")
    }
}
