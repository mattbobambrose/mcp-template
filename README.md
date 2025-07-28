# Kotlin MCP Template

[![Kotlin](https://img.shields.io/badge/%20language-Kotlin-red.svg)](https://kotlinlang.org/)

## Overview

This repository provides a Kotlin template for building MCP (Model Context Protocol)
servers that integrate custom tools with MCP clients. Built with the JetBrains
[Kotlin MCP SDK](https://github.com/modelcontextprotocol/kotlin-sdk),
it demonstrates SSE (Server-Sent Events) and STDIO transport methods and
how to integrate custom tools. For more details on MCP, have a look
[here](https://www.youtube.com/watch?v=TTtQxUprbDY&list=PLbAT2y1IaF3iZJPWJuIQtpSA924lv_zJP).

## Setup

* Click on
  the [![](https://github.com/mattbobambrose/mcp-template/blob/master/docs/template_button.png)button](https://github.com/mattbobambrose/mcp-template/generate)
  above to clone the template repo and create your own MCP server.

* Or use the [gh](https://github.com/cli/cli) CLI command:

```bash
gh repo create my-mcp-server --template mattbobambrose/mcp-template --public --clone
cd my-mcp-server
```

* Open the repo in [Intellij](https://www.jetbrains.com/idea/).

## Building an MCP Server with SSE

* Run the SSE server from Intellij by running the main() in SSEMain.kt
* To run the server from the CLI, build the jar with `./gradlew sse` and run it with
  `java -jar ./build/libs/SSEServer.jar`

## Building an MCP Server with STDIO

* Build the jar with `./gradlew stdio` and run it with the desired MCP client.

## Testing with MCP Inspector

Launch the [MCP Inspector](https://github.com/modelcontextprotocol/inspector) with:

```bash 
DANGEROUSLY_OMIT_AUTH=true npx @modelcontextprotocol/inspector
```

and go to the inspector with your browser and try the following configurations:

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

## Working with Claude Desktop and SSE

* Download [Claude Desktop](https://claude.ai/download) and sign in if needed.
* Start the server using either `java -jar ./build/libs/SSEServer.jar` or `docker run -p 8080:8080 my-mcp-server`
* Open Claude Desktop and go to Claude -> Settings -> Developer -> Edit Config
* Edit `claude_desktop_config.json` as follows:

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

## Docker Configuration

MCP servers running in a Docker container use `SSEServer.jar`

* Build the jar with `./gradlew sse`
* Build the image with `docker build -t my-mcp-server .`
* Run the container with `docker run -p 8080:8080 my-mcp-server`
* Confirm that the container is running using the MCP Inspector as described above in the SSE Configuration section.

## Tools

There are two ways to add new tools in `MCPServer.kt`:

1. Create a class such as `Tools.kt` and create functions annotated with `@LLMTool`
2. Call the `addTool()` method as described in
   the [kotlin sdk](https://github.com/modelcontextprotocol/kotlin-sdk/blob/b19d9f174691ae14d0369c7ced6c2e2723ccc0b2/src/commonMain/kotlin/io/modelcontextprotocol/kotlin/sdk/server/Server.kt#L196) 
