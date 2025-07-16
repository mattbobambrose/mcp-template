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
  `java -jar ./build/libs/SSEServer.jar`.

## Building an STDIO Server

* Build the jar with `./gradlew StdioServer` and run with the desired MCP client.

## Testing with MCP Inspector

Launch the [mcp inspector](https://github.com/modelcontextprotocol/inspector) in a terminal window with
```bash 
DANGEROUSLY_OMIT_AUTH=true npx @modelcontextprotocol/inspector
```

### SSE Configuration

* Select SSE for the `Transport Type`
* Enter `https&#58;&#47;&#47:localhost:8080/sse` for the `URL`

## Working with Claude Desktop

Download [Claude Desktop](https://claude.ai/download) and sign in if needed.
Open Claude Desktop and go to Claude -> Settings -> Developer -> Edit Config.
There will be a file called claude_desktop_config.json. Open that in your choice
of IDE. It should be formatted as such:

```JSON
{
  "mcpServers": {
    "weather-stdio": {
      "command": "java",
      "args": [
        "-jar",
        "/Users/mambrose/git/mcp-template/build/libs/StdioServer.jar"
      ]
    },
    "weather-sse": {
      "command": "npx",
      "args": [
        "-y",
        "mcp-remote",
        "http://localhost:8080/sse"
      ]
    },
    "mydocker": {
      "command": "npx",
      "args": [
        "mcp-remote",
        "http://127.0.0.1:5000/mcp",
        "--transport",
        "http-first"
      ]
    }
  }
}
```

Choose either the ```"weather-stdio"``` block to connect with stdio,
the ```"weather-sse"``` block to connect with sse,
or the ```"mydocker"``` block to connect with docker.

Quit and reopen Claude Desktop, check the Search and Tools button for the tools
available to the llm, and ask Claude a question that custom tools can answer.
If it uses web search to get its answer, include "Use the tools provided and
don't use the web" in your prompt.

When debugging Claude Desktop, running this in the terminal will give you an idea of why your MCP connections are
failing:

```bash
tail -n 20 -F ~/Library/Logs/Claude/mcp*.log
```

## Tools

There are two ways to add new tools to the MCP server:

1. Create a class such as `Tools.kt` and create functions annotated with `@LLMTool`
2. Call the `addTool()` function in createServer() in `MCPServer.kt`
