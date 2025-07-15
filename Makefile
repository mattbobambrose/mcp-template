clean:
	./gradlew clean

build: clean
	./gradlew build

jar: build
	./gradlew SSEServer StdioServer

runsse:
	java -jar ./build/libs/SSEServer.jar

build-docker: jar
	docker build -t my-mcp-server .

run-docker:
	docker run -p 8080:8080 my-mcp-server