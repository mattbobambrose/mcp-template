package com.mattbobambroseimport com.mattbobambrose.mcp_utils.tools.integrateTools4
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.ServerCapabilities
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions

fun createServer() =
  Server(
    Implementation(
      name = "ClimateTrace MCP Server",
      version = "1.0.0"
    ),
    ServerOptions(
      capabilities = ServerCapabilities(
        tools = ServerCapabilities.Tools(listChanged = true)
      )
    )
  ).apply {
    integrateTools4(MyTools())
  }