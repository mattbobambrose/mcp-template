FROM alpine
LABEL maintainer="Matthew Ambrose <mattbobambrose@gmail.com>"
RUN apk add openjdk17-jre

# Define the user to use in this instance to prevent using root that even in a container, can be a security risk.
ENV APPLICATION_USER=mcp-user

# Then add the user, create the /mcp folder and give permissions to our user.
RUN adduser --disabled-password --gecos '' $APPLICATION_USER

RUN mkdir /mcp
RUN chown -R $APPLICATION_USER /mcp

# Mark this container to use the specified $APPLICATION_USER
USER $APPLICATION_USER

# Make /mcp the working directory
WORKDIR /mcp

COPY ./build/libs/SSEServer.jar /mcp/SSEServer.jar

EXPOSE 8080

CMD []

ENTRYPOINT ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "/mcp/SSEServer.jar"]