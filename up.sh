#!/bin/bash
export BACKEND_URL=http://localhost:3000
export RIEMANN_HOST=127.0.0.1 
lein ring server-headless 4000
