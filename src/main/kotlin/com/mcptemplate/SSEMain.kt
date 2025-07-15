package com.mattbobambroseimport io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.modelcontextprotocol.kotlin.sdk.server.mcp

fun main() {
  val server = createServer()

  println("Starting the server at http://localhost:8080")
  embeddedServer(CIO, host = "0.0.0.0", port = 8080) {
    mcp {
      server
    }
  }.start(wait = true)
}