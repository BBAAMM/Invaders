package com.mirim.infra.logging

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.logging.Formatter
import java.util.logging.LogRecord

class MinimalFormatter : Formatter() {

    private val dtf = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        .withZone(ZoneId.systemDefault())

    override fun format(record: LogRecord): String {
        val time = dtf.format(Instant.ofEpochMilli(record.millis))
        val level = record.level.name.padEnd(7)
        val logger = record.loggerName ?: "root"
        val msg = formatMessage(record)

        val ex = record.thrown?.let { t ->
            val stack = t.stackTraceToString()
            "\n$stack"
        } ?: ""

        return "$time [$level] $logger - $msg$ex\n"
    }
}
