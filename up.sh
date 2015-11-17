#!/bin/bash
: ${PORT:=4000}
echo Starting server on port $PORT
export RIEMANN_HOST=127.0.0.1 
export SQUARE_SIZER_URL=http://192.168.59.103:4002
export NORMALISER_URL=http://192.168.59.103:4001
export COLUMN_HANDLER_URL=http://192.168.59.103:4003
lein ring server-headless $PORT

