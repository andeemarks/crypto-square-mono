#!/bin/bash
: ${PORT:=3002}
echo Starting server on port $PORT
RIEMANN_URL=127.0.0.1 lein ring server-headless $PORT
