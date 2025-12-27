package com.mirim.infra.logging

import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.logging.*

object Logging {

    /**
     * 앱 시작 시 1회 호출.
     * IntelliJ 실행/배포 모두에서 안정적으로 동작하도록 logs/ 아래에 날짜별 파일을 생성.
     */
    fun init(
        appName: String = "invaders",
        level: Level = Level.INFO,
        logsDir: Path = Path.of("logs")
    ) {
        Files.createDirectories(logsDir)

        val logger = Logger.getLogger("") // root logger
        logger.level = level

        // 기존 핸들러 제거(중복 출력 방지)
        logger.handlers.forEach { logger.removeHandler(it) }

        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
        val filePath = logsDir.resolve("${appName}_$timestamp.log").toString()

        val fileHandler = FileHandler(filePath, true).apply {
            formatter = MinimalFormatter()
            this.level = level
        }

        val consoleHandler = ConsoleHandler().apply {
            formatter = MinimalFormatter()
            this.level = level
        }

        logger.addHandler(fileHandler)
        logger.addHandler(consoleHandler)
    }
}
