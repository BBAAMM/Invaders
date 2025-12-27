package com.mirim.controller.loop

import java.util.concurrent.locks.LockSupport

class FixedStepLoop(
    private val ups: Int,
    private val onTick: () -> Unit
) : Runnable {

    @Volatile private var running = false
    private var thread: Thread? = null

    fun start() {
        if (running) return
        running = true
        thread = Thread(this, "fixed-step-loop").also { it.start() }
    }

    fun stop() {
        running = false
        thread?.interrupt()
        thread?.join()
    }

    override fun run() {
        val targetUps = ups.coerceAtLeast(1)
        val nsPerUpdate = 1_000_000_000L / targetUps

        var next = System.nanoTime()

        while (running) {
            val now = System.nanoTime()

            if (now >= next) {
                // 뒤처졌다면 따라잡기 (스파이럴 방지 위해 상한을 둠)
                var updates = 0
                while (running && System.nanoTime() >= next && updates < 5) {
                    onTick()
                    next += nsPerUpdate
                    updates++
                }
                // 너무 뒤처지면 현재 시점으로 재정렬
                if (updates == 5) {
                    next = System.nanoTime() + nsPerUpdate
                }
            } else {
                val sleepNs = (next - now).coerceAtLeast(0L)
                // 너무 길게 잠들지 않게 상한 (선택)
                val capped = sleepNs.coerceAtMost(2_000_000L) // 2ms
                LockSupport.parkNanos(capped)
            }
        }
    }
}
