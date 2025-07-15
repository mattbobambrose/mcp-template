package com.mcptemplate

import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool
import io.modelcontextprotocol.kotlin.sdk.server.mcp

fun main() {
  embeddedServer(CIO, host = "0.0.0.0", port = 8080) {
    mcp {
      createServer().apply {
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
    }
    routing {
      get("/ping") {
        call.respondText("pong")
      }
    }
  }.start(wait = true)
}
