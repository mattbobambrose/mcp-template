package com.mcptemplate

import com.mattbobambrose.mcp_utils.tools.integrateTools
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.ServerCapabilities
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
    integrateTools(MyTools())
  }