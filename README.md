# Kotlin MCP Template

[![Kotlin](https://img.shields.io/badge/%20language-Kotlin-red.svg)](https://kotlinlang.org/)

## Overview

Jetbrains provides an sdk that implements MCP in Kotlin. This template provides
an easy out-of-box experience that demonstrates how to call custom tools
in Claude Desktop via MCP servers.

# Creating a server

1. Create a repository from [this template](https://github.com/mattbobambrose/mcp-template)
2. Download [Claude Desktop] (https://claude.ai/download) and sign in if needed.
3. In an Intellij terminal, either run ```make jar runsse``` or run SSEMain.
4. Launch the [mcp inspector] (https://github.com/modelcontextprotocol/inspector) in a terminal with
```bash 
npx @modelcontextprotocol/inspector
```

If you receive the error `Connection Error - Check if your MCP server is running and proxy token is correct`,
then run with authentication turned off with

```bash
DANGEROUSLY_OMIT_AUTH=true npx @modelcontextprotocol/inspector
```

5. Open Claude Desktop and go to Claude -> Settings -> Developer -> Edit Config.
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

6. Quit and reopen Claude Desktop, check the Search and Tools button for the tools
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