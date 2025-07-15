package com.mcptemplate

import com.mattbobambrose.mcp_utils.tools.LLMTool

class MyTools {
  @LLMTool("List of cities in California")
  fun getCities(): List<String> {
    return listOf("Diablo", "Danville", "San Francisco", "San Ramon", "Sacramento")
  }

  @LLMTool("Get the temperature of a city in California")
  fun getTemperature(city: String): String {
    return when (city) {
      "Diablo" -> "50 degrees"
      "Danville" -> "60 degrees"
      "San Francisco" -> "70 degrees"
      "San Ramon" -> "80 degrees"
      "Sacramento" -> "90 degrees"
      else -> "The city was not in the list"
    }
  }
}
