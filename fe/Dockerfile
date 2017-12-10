FROM clojure

ENV workdir=/usr/src/app PORT=80 SERVICE_HOST=backend

RUN mkdir -p ${workdir}

WORKDIR ${workdir}

COPY project.clj ${workdir}

RUN lein deps

COPY . ${workdir}

EXPOSE ${PORT}

ENTRYPOINT ["/usr/src/app/up.sh"]
