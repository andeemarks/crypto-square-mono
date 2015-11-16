FROM clojure

ENV workdir /usr/src/app

RUN mkdir -p ${workdir}

WORKDIR ${workdir}

COPY project.clj ${workdir}

RUN lein deps

COPY . ${workdir}

EXPOSE 3002

ENTRYPOINT ["/usr/src/app/up.sh"]
