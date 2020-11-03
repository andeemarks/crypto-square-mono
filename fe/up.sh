#!/bin/bash
: ${PORT:=4000}
: ${SERVICE_HOST:=backend}
echo Starting server on port $PORT
export BACKEND_URL=http://${SERVICE_HOST}
lein ring server-headless $PORT
