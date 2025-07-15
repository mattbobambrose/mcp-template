package com.mcptemplate

import com.mattbobambrose.mcp_utils.tools.integrateTools
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.ServerCapabilities
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions

fun createServer() =
  Server(
    Implementation(
      name = "MCP Server Template",
      version = "1.0.0"
    ),
    ServerOptions(
      capabilities = ServerCapabilities(
        prompts = ServerCapabilities.Prompts(listChanged = true),
        resources = ServerCapabilities.Resources(subscribe = true, listChanged = true),
        tools = ServerCapabilities.Tools(listChanged = true)
      )
    )
  ).apply {
    // Adding tool via mcp_utils
    integrateTools(MyTools())
    // Adding tool via explicit addTools() call
    addTool(
      name = "kotlin-sdk-tool",
      description = "A test tool",
      inputSchema = Tool.Input()
    ) { request ->
      CallToolResult(
        content = listOf(TextContent("Hello, world!"))
      )
    }
  }