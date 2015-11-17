#!/bin/bash
: ${PORT:=4000}
: ${SERVICE_HOST:=192.168.59.103}
echo Starting server on port $PORT
export BACKEND_URL=http://${SERVICE_HOST}:4000
export RIEMANN_HOST=127.0.0.1 
lein ring server-headless $PORT
