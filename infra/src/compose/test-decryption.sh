#!/bin/bash
# Bash script to test the decryption of encrypted properties

# Read the encrypted properties from the JSON file
JSON_FILE="./compose.dev.json"

# Function to decode base64 in a cross-platform way
decode_base64() {
    if command -v base64 >/dev/null 2>&1; then
        echo "$1" | base64 --decode
    else
        python -c "import base64, sys; print(base64.b64decode(sys.argv[1]).decode('utf-8'))" "$1"
    fi
}

echo "Testing decryption of encrypted properties:"
echo "----------------------------------------"

# Extract and decrypt properties using jq if available, or Python as fallback
if command -v jq >/dev/null 2>&1; then
    # Using jq to parse JSON
    for key in $(jq -r '.encrypted_properties | keys[]' "$JSON_FILE"); do
        encrypted_value=$(jq -r ".encrypted_properties.\"$key\"" "$JSON_FILE")
        decrypted_value=$(decode_base64 "$encrypted_value")
        echo "$key: $decrypted_value"
    done
else
    # Fallback to Python for JSON parsing
    python -c "
import json, base64, sys
with open('$JSON_FILE', 'r') as f:
    data = json.load(f)
for key, value in data['encrypted_properties'].items():
    decrypted = base64.b64decode(value).decode('utf-8')
    print(f'{key}: {decrypted}')
"
fi

echo "----------------------------------------"
echo "Decryption test completed. If you can see the decrypted values above, the decryption process is working correctly."