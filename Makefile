clean:
	./gradlew clean

build: clean
	./gradlew build

sse: build
	./gradlew sse

stdio: build
	./gradlew stdio

jars: sse stdio
	./gradlew sse stdio

runsse:
	java -jar ./build/libs/SSEServer.jar

build-docker: jars
	docker build -t my-mcp-server .

run-docker:
	docker run -p 8080:8080 my-mcp-server