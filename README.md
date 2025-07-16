# Kotlin MCP Template

[![Kotlin](https://img.shields.io/badge/%20language-Kotlin-red.svg)](https://kotlinlang.org/)

## Overview

Jetbrains provides an sdk that implements MCP in Kotlin. This template provides
an easy out-of-box experience that demonstrates how to call custom tools
in Claude Desktop via MCP servers.

## Setup

* Click on
  the [![](https://github.com/mattbobambrose/mcp-template/blob/master/docs/template_button.png)button](https://github.com/mattbobambrose/mcp-template/generate)
  above to clone the template repo and create your own MCP server.

* Open the code in [Intellij](https://www.jetbrains.com/idea/).

## Building an SSE Server

* The SSE server can be run from Intellij by running the main() in SSEMain.kt
* To run from the CLI, build the jar with `./gradlew SSEServer` and run with
  `java -jar ./build/libs/SSEServer.jar`

## Building an STDIO Server

* Build the jar with `./gradlew StdioServer` and run with the desired MCP client.

## Testing with MCP Inspector

Launch the [mcp inspector](https://github.com/modelcontextprotocol/inspector) in a terminal window with
```bash 
DANGEROUSLY_OMIT_AUTH=true npx @modelcontextprotocol/inspector
```

### SSE Configuration

* Select SSE for the `Transport Type`.
* Enter `http://localhost:8080/sse` for the `URL`
* Click on `Connect`.
* Click on the `Tools` icon and `List Tools` to see what tools are available.
* Click on `getCities` tools entry and then click on `Run Tool` to verify the results.
* `getTemperature` causes problems in the MCP Inspector, so don't test it for now.

### STDIO Configuration

* Select STDIO for the `Transport Type`
* Enter `java` for the `Command` and `-jar /<absolute-path-to-repo>/mcp-template/build/libs/StdioServer.jar` for the
  `Arguments`
* Click on `Connect`
* Click on the `Tools` icon and `List Tools` to see what tools are available.
* Click on `getCities` tools entry and then click on `Run Tool` to verify the results.
* `getTemperature` causes problems in the MCP Inspector, so don't test it for now.
* Note: If you recompile the jar, you will need to restart the MCP Inspector as well.

### Docker Configuration

The Docker configuration will create an image using the SSEServer.jar.

* Build the jar with `./gradlew sse`
* Build the image with `docker build -t my-mcp-server .`
* Run the container with `docker run -p 8080:8080 my-mcp-server`
* Confirm that the container is running using the MCP Inspector as described above in the SSE Configuration section.

## Working with Claude Desktop and SSE

* Download [Claude Desktop](https://claude.ai/download) and sign in if needed.
* Start the server using either `java -jar ./build/libs/SSEServer.jar` or `docker run -p 8080:8080 my-mcp-server`
* Open Claude Desktop and go to Claude -> Settings -> Developer -> Edit Config.
* If you're running the jar file, edit `claude_desktop_config.json` as follows:

###  
```JSON
{
  "mcpServers": {
    "sse-mcp-template": {
      "command": "npx",
      "args": [
        "-y",
        "mcp-remote",
        "http://localhost:8080/sse"
      ]
    }
  }
}
```

* If you're running the Docker container, edit `claude_desktop_config.json` as follows:

```JSON
{
  "mcpServers": {
    "docker-mcp-template": {
      "command": "npx",
      "args": [
        "mcp-remote",
        "http://localhost:8080/mcp",
        "--transport",
        "http-first"
      ]
    }
  }
}
```

* Restart Claude Desktop, check the Search and Tools button for the tools
  available to the LLM, and ask the following questions:

```
What are the cities in California? 
What is the hottest city in California?
What are the cities in Massachusetts?
```

To demonstrate the precision with which tools are called, ask:

```
What is the hottest city in Massachusetts?
```

* Make sure you restart Claude Desktop after any edits on `claude_desktop_config.json`

When debugging Claude Desktop, the log is available with:

```bash
tail -n 20 -F ~/Library/Logs/Claude/mcp*.log
```

## Tools

There are two ways to add new tools to the MCP server:

1. Create a class such as `Tools.kt` and create functions annotated with `@LLMTool`
2. Call the `addTool()` function in createServer() in `MCPServer.kt`
