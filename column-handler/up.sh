#!/bin/bash
: ${PORT:=4000}
echo Starting server on port $PORT
echo Test me with: "curl http://localhost:$PORT/abcd/2 | jq"
lein ring server-headless $PORT
