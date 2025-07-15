As described in https://github.com/modelcontextprotocol/inspector, launch in the terminal with

```bash 
npx @modelcontextprotocol/inspector
```

and run with authentication turned off with

```bash
DANGEROUSLY_OMIT_AUTH=true npx @modelcontextprotocol/inspector
```

The config file should look like

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

When debugging Claude Desktop, running this in the terminal will give you an idea of why your MCP connections are
failing:

```bash
tail -n 20 -F ~/Library/Logs/Claude/mcp*.log
```
