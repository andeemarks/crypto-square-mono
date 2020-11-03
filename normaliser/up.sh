#!/bin/bash
: ${PORT:=4000}
echo Starting server on port $PORT
lein ring server-headless $PORT
