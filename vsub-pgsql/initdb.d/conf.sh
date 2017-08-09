#!/bin/bash

set -e

cp /docker-entrypoint-initdb.d/postgresql.conf ${PGDATA}
