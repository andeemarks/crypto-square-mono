#!/bin/bash
: ${PORT:=4000}
echo Starting server on port "$PORT"
export SQUARE_SIZER_URL=http://square-sizer
export NORMALISER_URL=http://normaliser
export COLUMN_HANDLER_URL=http://column-handler
echo Test with "curl http://localhost:$PORT/health | jq"
lein ring server-headless "$PORT"

