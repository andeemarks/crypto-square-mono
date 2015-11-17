FROM clojure

ENV workdir=/usr/src/app PORT=80 SERVICE_HOST=192.168.59.103

RUN mkdir -p ${workdir}

WORKDIR ${workdir}

COPY project.clj ${workdir}

RUN lein deps

COPY . ${workdir}

EXPOSE ${PORT}

ENTRYPOINT ["/usr/src/app/up.sh"]
