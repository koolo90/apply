# Docker Compose with Encrypted Properties

This directory contains Docker Compose configuration files and scripts for running the application with encrypted sensitive properties.

## Files

- `compose.dev.yml`: The main Docker Compose configuration file with environment variables using the `${VARIABLE}` syntax
- `compose.dev.json`: Contains the encrypted properties (base64 encoded)
- `run-compose-dev.ps1`: PowerShell script for Windows users to run Docker Compose with decrypted properties
- `run-compose-dev.sh`: Bash script for Linux/Mac users to run Docker Compose with decrypted properties

## How It Works

1. Sensitive properties like database credentials are stored in encrypted form in the `compose.dev.json` file
2. The scripts read these encrypted properties, decrypt them, and set them as environment variables
3. Docker Compose uses these environment variables when starting the containers

## Running the Application

### On Windows

```powershell
cd infra\src\compose
.\run-compose-dev.ps1
```

### On Linux/Mac

```bash
cd infra/src/compose
chmod +x run-compose-dev.sh
./run-compose-dev.sh
```

## Testing the Decryption Process

To verify that the decryption process works correctly without running Docker Compose:

### On Windows

```powershell
cd infra\src\compose
.\test-decryption.ps1
```

### On Linux/Mac

```bash
cd infra/src/compose
chmod +x test-decryption.sh
./test-decryption.sh
```

## Adding or Updating Encrypted Properties

To add or update an encrypted property:

1. Generate the base64 encoded value:
   - Windows PowerShell: `[Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes("your-value"))`
   - Linux/Mac: `echo -n "your-value" | base64`

2. Add or update the property in the `compose.dev.json` file:
   ```json
   {
     "encrypted_properties": {
       "YOUR_PROPERTY_NAME": "base64-encoded-value"
     }
   }
   ```

3. Reference the property in `compose.dev.yml` using the `${VARIABLE}` syntax:
   ```yaml
   environment:
     - YOUR_PROPERTY_NAME=${YOUR_PROPERTY_NAME}
   ```

## Security Considerations

Note that base64 encoding is not true encryption but rather obfuscation. For production environments, consider using a more secure encryption method or a secrets management solution like Docker Secrets, HashiCorp Vault, or AWS Secrets Manager.
