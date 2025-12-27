package com.mirim.infra.config

import kotlinx.serialization.json.Json
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

class JsonConfigLoader(
    val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }
) {
    inline fun <reified T> loadFromText(text: String): T =
        json.decodeFromString<T>(text)

    inline fun <reified T> loadResource(resourcePath: String): T {
        val stream = Thread.currentThread().contextClassLoader
            .getResourceAsStream(resourcePath)
            ?: throw IllegalArgumentException("Resource not found on classpath: $resourcePath")

        val text = stream.bufferedReader(StandardCharsets.UTF_8).use { it.readText() }
        return loadFromText(text)
    }
}
