#!/bin/bash
: ${PORT:=4000}
: ${SERVICE_HOST:=backend}
echo Starting server on port $PORT
export BACKEND_URL=http://${SERVICE_HOST}
export RIEMANN_HOST=127.0.0.1 
lein ring server-headless $PORT
