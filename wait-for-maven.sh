#!/bin/bash
echo "Waiting for Maven to finish..."

# Loop até que o arquivo JAR seja gerado
while [ ! -f /app/target/helpdesk-0.0.1-SNAPSHOT.jar ]; do
    sleep 2
done

echo "JAR file found. Starting app..."
exec "$@"
