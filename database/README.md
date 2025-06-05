# Database Module

This module contains database migration scripts and tools for managing the database schema.

## Migration Scripts

Migration scripts are located in `src/main/resources/db/migration` and follow the Flyway naming convention:

- `V1__create_users_table.sql` - Creates the users table
- `V2__insert_sample_users.sql` - Inserts sample user data
- `V3__create_roles_tables.sql` - Creates tables related to roles

## Running Migrations

Make sure the database container is running (see the compose file in `infra/src/compose/compose.dev.yml`) before running migrations.

### Using Gradle

Run migrations using Gradle with the following command:

```
./gradlew :database:migrateDatabase
```

This command will:
1. Connect to the database using the configured connection details
2. Apply any pending migrations from the `src/main/resources/db/migration` directory
3. Display information about the migration process

### Viewing Flyway Configuration

To view the current Flyway configuration:

```
./gradlew :database:showFlywayConfig
```

This will display the database URL, user, migration locations, and other Flyway settings.

### Environment Variables

The migration process uses the following environment variables:

- `SPRING_DATASOURCE_URL` - The JDBC URL for the database (default: `jdbc:postgresql://localhost:5432/applydb`)
- `POSTGRES_USER` - The database username (default: `postgres`)
- `POSTGRES_PASSWORD` - The database password (default: `postgres`)

## Adding New Migrations

To add a new migration:

1. Create a new SQL file in `src/main/resources/db/migration`
2. Name it following the Flyway convention: `V{version}__{description}.sql`
   - The version should be incremented from the highest existing version
   - The description should be a brief explanation of what the migration does, with words separated by underscores
3. Write your SQL statements in the file
4. Run the migrations to apply the changes

## Troubleshooting

If you encounter issues with migrations:

1. Make sure the database container is running
2. Check that the environment variables are set correctly
3. Look at the Flyway schema history table (`flyway_schema_history`) to see which migrations have been applied
4. If necessary, you can clean the database and start fresh with `./gradlew :database:flywayClean`
