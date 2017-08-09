#!/usr/bin/env bash

set -e

NGINX_DEST="/nginx/sites-enabled"
[[ ! -d "${NGINX_DEST}" ]] && mkdir -p "${NGINX_DEST}"

[[ "${NGINX_PROD}" -eq 1 ]] && PATTERN="*.prod.conf" || PATTERN="*.dev.conf"

for CONF_FILE in `find ./vsub-nginx/sites-enabled -type f -name ${PATTERN}`; do
    echo "      - installing ${CONF_FILE}"
	sed -i -e "s/{{NGINX_DOMAIN}}/${NGINX_DOMAIN}/g" ${CONF_FILE}
	cp -rp "${CONF_FILE}" "${NGINX_DEST}"
done;