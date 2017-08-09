#!/bin/bash

set -e

# Def variables
CONNEXION_STRING="jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DB}"

function jar() {
    echo /${PROJECT_NAME}/$1/target/$1-$2.jar
}

function resources() {
    echo /${PROJECT_NAME}/$1/src/main/resources/$2
}

# Init configuration for Java Spring Boot
echo "[DEBUG] > Init configuration."
sed -i \
    -e "s/{{PG_HOST}}/${POSTGRES_HOST}/g" \
    -e "s/{{PG_DB}}/${POSTGRES_DB}/g" \
    -e "s/{{PG_PORT}}/${POSTGRES_PORT}/g" \
    -e "s/{{PG_USER}}/${POSTGRES_USER}/g" \
    -e "s/{{PG_PWD}}/${POSTGRES_PASSWORD}/g" \
    -e "s/{{JWT_SECRET}}/${JWT_SECRET}/g" \
    $(resources "${API_NAME}" "application.properties")
echo "[DEBUG] > Configuration ok."

# Nginx stuff
echo "[DEBUG] > Install nginx configuration"
source ./vsub-nginx/install.sh
echo "[DEBUG] > Install OK."

# CLean install and build packages
echo "[DEBUG] > Build packages"
[[ "${DEBUG}" -eq 0 ]] && arg="clean" || arg=""
mvn ${arg} install package || exit 1
echo "[DEBUG] > Build OK."

# Test database live
maxTriesPostgres=10
echo "[DEBUG] > Check POSTGRES availability"
while ! nc -w 1 "${POSTGRES_HOST}" "${POSTGRES_PORT}" 1>/dev/null 2>&1
do
    [[ "${maxTriesPostgres}" -eq 0 ]] && echo "Postgres database is not available." && exit 1
    sleep 5
    maxTriesPostgres=$((maxTriesPostgres - 1))
done
echo "[DEBUG] > POSTGRES is available."

# Test database connexion
echo "[DEBUG] > Check database connection"
java -jar $(jar "${CONSOLE_NAME}" "${VERSION_CONSOLE}") \
    "check" "${CONNEXION_STRING}" "${POSTGRES_USER}" "${POSTGRES_PASSWORD}" || exit 1
echo "[DEBUG] > Database is available to connections."

# Migrate database
echo "[DEBUG] > Migrate database"
java -jar $(jar "${CONSOLE_NAME}" "${VERSION_CONSOLE}") \
    "migrate" "${CONNEXION_STRING}" "${POSTGRES_USER}" "${POSTGRES_PASSWORD}" || exit 1
echo "[DEBUG] > Database is migrated."

# Launch server
echo "[DEBUG] > Launch server."

# Enable debug mode
if [[ "${DEBUG}" -eq 1 ]]; then
    echo "[DEBUG] > DEBUG MODE ENABLED"
    ARG="-Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
fi

java ${ARG} -jar $(jar ${API_NAME} ${VERSION_API})