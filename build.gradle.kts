import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.ktor)
  alias(libs.plugins.versions)
  application
}

application {
  mainClass = "SSEMainKt"
}

group = "com.mcptemplate"
version = "1.0"

repositories {
  mavenCentral()
  maven { url = uri("https://jitpack.io") }
}

dependencies {
  implementation(libs.mcp.kotlin)
//  implementation("ch.qos.logback:logback-classic:1.4.14")
//    implementation(libs.slf4j)
  implementation(libs.ktor.client.content.negotation)
  implementation(libs.utils.json)
  implementation(libs.mcp.tools)
  testImplementation(libs.kotlin.test)
}

tasks.test {
  useJUnitPlatform()
}
kotlin {
  jvmToolchain(17)
}

configurations.all {
  resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

tasks.register<ShadowJar>("sse") {
  dependsOn(tasks.shadowJar)
  archiveFileName = "SSEServer.jar"
  manifest {
    attributes["Main-Class"] = "com.mcptemplate.SSEMainKt"
  }
  from(sourceSets.main.get().output)
  configurations = listOf(project.configurations.runtimeClasspath.get())
}

tasks.register<ShadowJar>("stdio") {
  dependsOn(tasks.shadowJar)
  archiveFileName = "StdioServer.jar"
  manifest {
    attributes["Main-Class"] = "com.mcptemplate.StdioMainKt"
  }
  from(sourceSets.main.get().output)
  configurations = listOf(project.configurations.runtimeClasspath.get())
}
