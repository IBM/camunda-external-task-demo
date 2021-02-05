FROM open-liberty

ADD --chown=1001:0 target/camunda-embedded-engine-demo.war /config/dropins
COPY --chown=1001:0 src/release/ocp/server.xml /config/
COPY --chown=1001:0 src/lib/* /lib.index.cache/
