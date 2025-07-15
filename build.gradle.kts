import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  alias(libs.plugins.kotlin.jvm)
//  alias(libs.plugins.shadow)
  alias(libs.plugins.ktor)
  application
}

application {
  mainClass = "SSEMainKt"
}

tasks.register<ShadowJar>("SSEServer") {
  dependsOn(tasks.shadowJar)
  archiveFileName = "SSEServer.jar"
  manifest {
    attributes["Main-Class"] = "com.mcptemplate.SSEMainKt"
  }
  from(sourceSets.main.get().output)
  configurations = listOf(project.configurations.runtimeClasspath.get())
}

tasks.register<ShadowJar>("StdioServer") {
  dependsOn(tasks.shadowJar)
  archiveFileName = "StdioServer.jar"
  manifest {
    attributes["Main-Class"] = "com.mcptemplate.StdioMainKt"
  }
  from(sourceSets.main.get().output)
  configurations = listOf(project.configurations.runtimeClasspath.get())
}

group = "com.mcptemplate"
version = "1.0"

repositories {
  mavenCentral()
  maven { url = uri("https://jitpack.io") }
}

dependencies {
  implementation(libs.mcp.kotlin)
  implementation("ch.qos.logback:logback-classic:1.4.14")
//    implementation(libs.slf4j)
  implementation(libs.ktor.client.content.negotation)
  implementation(libs.ktor.serialization)
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
