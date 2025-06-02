# PowerShell script to test the decryption of encrypted properties

# Read the encrypted properties from the JSON file
$jsonContent = Get-Content -Path ".\compose.dev.json" | ConvertFrom-Json

# Decrypt the properties and display them
Write-Host "Testing decryption of encrypted properties:"
Write-Host "----------------------------------------"

$jsonContent.encrypted_properties.PSObject.Properties | ForEach-Object {
    $decryptedValue = [System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($_.Value))
    Write-Host "$($_.Name): $decryptedValue"
}

Write-Host "----------------------------------------"
Write-Host "Decryption test completed. If you can see the decrypted values above, the decryption process is working correctly."