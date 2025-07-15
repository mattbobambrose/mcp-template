package com.mcptemplate

import io.ktor.utils.io.streams.asInput
import io.modelcontextprotocol.kotlin.sdk.server.StdioServerTransport
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.io.asSink
import kotlinx.io.buffered

fun main() {
  val transport = StdioServerTransport(
    System.`in`.asInput(),
    System.out.asSink().buffered()
  )

  runBlocking {
    createServer().apply {
      connect(transport)
      val done = Job()
      onClose {
        done.complete()
      }
      done.join()
    }
  }
}