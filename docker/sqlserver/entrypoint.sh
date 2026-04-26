#!/bin/bash
set -e

# Start SQL Server in the background
/opt/mssql/bin/sqlservr &
MSSQL_PID=$!

echo "[init] Waiting for SQL Server to become available..."
until /opt/mssql-tools18/bin/sqlcmd \
        -S localhost -U SA -P "$SA_PASSWORD" \
        -Q "SELECT 1" -C -No > /dev/null 2>&1; do
    sleep 1
done

echo "[init] Creating database '${MSSQL_DB}' if it does not exist..."
/opt/mssql-tools18/bin/sqlcmd \
    -S localhost -U SA -P "$SA_PASSWORD" -C -No \
    -Q "IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'${MSSQL_DB}')
            CREATE DATABASE [${MSSQL_DB}];
        PRINT 'Database ready: ${MSSQL_DB}';"

echo "[init] Done."

# Hand control back to SQL Server
wait $MSSQL_PID
