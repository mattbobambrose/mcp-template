package com.mattbobambroseimport com.mattbobambrose.mcp_utils.LLMTool


class MyTools {
  @LLMTool("List of cities in California")
  fun getCities(): List<String> {
    return listOf("Diablo", "Danville", "San Francisco", "San Ramon", "Sacramento")
  }

  @LLMTool("Get the temperature of a city in California")
  fun getTemperatures(city: String): String {
    return when (city) {
      "Diablo" -> "50 degrees"
      "Danville" -> "60 degrees"
      "San Francisco" -> "70 degrees"
      "San Ramon" -> "80 degrees"
      "Sacramento" -> "90 degrees"
      else -> "The city was not in the list"
    }
  }

  @LLMTool("Get weather information with optional temperature unit")
  fun getWeather(city: String, unit: String = "fahrenheit"): String {
    val temp = when (city) {
      "Diablo" -> 50
      "Danville" -> 60
      "San Francisco" -> 70
      "San Ramon" -> 80
      "Sacramento" -> 90
      else -> return "City not found"
    }

    val convertedTemp = if (unit.lowercase() == "celsius") {
      ((temp - 32) * 5 / 9).toString()
    } else {
      temp.toString()
    }

    return "$convertedTemp degrees ${unit.lowercase()}"
  }

  @LLMTool("Get temperatures for multiple cities")
  fun getMultipleTemperatures(cities: List<String>): List<String> {
    return cities.map { city ->
      when (city) {
        "Diablo" -> "$city: 50 degrees"
        "Danville" -> "$city: 60 degrees"
        "San Francisco" -> "$city: 70 degrees"
        "San Ramon" -> "$city: 80 degrees"
        "Sacramento" -> "$city: 90 degrees"
        else -> "$city: City not found"
      }
    }
  }

  @LLMTool("Get unique cities from a set")
  fun getUniqueCities(cities: Set<String>): String {
    return "Unique cities: ${cities.joinToString(", ")}"
  }

  @LLMTool("Get city information from a map")
  fun getCityInfo(cityData: Map<String, String>): String {
    return cityData.entries.joinToString("; ") { "${it.key}: ${it.value}" }
  }

  @LLMTool("Get unique warm cities as a set")
  fun getWarmCities(): Set<String> {
    return setOf("San Ramon", "Sacramento", "San Francisco")
  }
}
