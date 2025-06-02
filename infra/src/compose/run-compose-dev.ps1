# PowerShell script to run Docker Compose with encrypted properties

# Read the encrypted properties from the JSON file
$jsonContent = Get-Content -Path ".\compose.dev.json" | ConvertFrom-Json

# Decrypt the properties and set them as environment variables
$jsonContent.encrypted_properties.PSObject.Properties | ForEach-Object {
    $decryptedValue = [System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($_.Value))
    [Environment]::SetEnvironmentVariable($_.Name, $decryptedValue)
    Write-Host "Set environment variable: $($_.Name)"
}

# Run Docker Compose
Write-Host "Starting Docker Compose with decrypted properties..."
docker-compose -f compose.dev.yml up