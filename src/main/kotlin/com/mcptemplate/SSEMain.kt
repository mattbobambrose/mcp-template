package com.mcptemplate

import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.modelcontextprotocol.kotlin.sdk.server.mcp

fun main() {
  embeddedServer(CIO, host = "0.0.0.0", port = 8080) {
    mcp {
      createServer()
    }
    routing {
      // Add an endpoint for checking for liveliness
      get("/ping") {
        call.respondText("pong")
      }
    }
  }.start(wait = true)
}
