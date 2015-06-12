#!/bin/bash
export RIEMANN_HOST=127.0.0.1 
export SQUARE_SIZER_URL=http://localhost:3001
export NORMALISER_URL=http://localhost:3002
export COLUMN_HANDLER_URL=http://localhost:3003
export SQUARE_SIZER_QUEUE=crypto-square.square-sizer.requests
lein ring server-headless 3000

