# A docker image for the domhauton.com website

FROM openjdk:10-jdk-slim as build-step
MAINTAINER Dominic Hauton <domhauton@gmail.com>
WORKDIR /home/domhauton-site-build

ADD ./src ./src
ADD ./gradle ./gradle
COPY ./build.gradle ./gradlew ./
# Uncomment exclude dockerskip line
RUN sed -i '/DOCKER-BUILD-ONLY/s/^\/\///' ./build.gradle
# Use wrapper instead of gradle image to ensure consistency
RUN ./gradlew --full-stacktrace --no-daemon build

FROM openjdk:10-jre-slim
WORKDIR /home/dockeruser
ENV INSTALL_DIR=/opt/domhauton-site

COPY --from=build-step /home/domhauton-site-build/build/libs $INSTALL_DIR/app
COPY --from=build-step /home/domhauton-site-build/build/output/libs $INSTALL_DIR/libs
COPY ./rootfs /

LABEL org.label-schema.name="domhauton-site" \
        org.label-schema.description="Dominic Hauton sample website." \
        org.label-schema.vcs-url="https://github.com/domhauton/site" \
        org.label-schema.usage="README.md" \
        org.label-schema.schema-version="1.0"

ENTRYPOINT java -cp "$INSTALL_DIR/libs/*:$(ls $INSTALL_DIR/app/*.jar)" com.domhauton.site.Main --config /config/domhauton-site/site-config.yml