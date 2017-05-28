package pw.kmp.gamelet.match.countdown

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import pw.kmp.gamelet.event.Subscribable

/**
 * A single countdown.
 */
class Countdown(val plugin: Plugin) : Subscribable() {

    var time: Int = -1
    var isRunning = false

    val runnable = object : BukkitRunnable() {
        override fun run() {
            if (time == 0) {
                cleanup()
                notify(End(false))
            } else {
                notify(Tick(time--))
            }
        }
    }

    /**
     * Start the countdown at the specified time.
     *
     * @param time the amount of time to run for
     */
    fun start(time: Int) {
        if (time < 1) throw IllegalArgumentException("Countdown must have a positive start time.")

        this.time = time
        isRunning = true
        runnable.runTaskTimer(plugin, 0, 20)
        notify(Start(time))
    }

    private fun cleanup() {
        isRunning = false
        runnable.cancel()
    }

    /**
     * Cancel the countdown prematurely.
     */
    fun cancel() {
        if (!isRunning) return
        cleanup()
        notify(End(true))
    }

    class Tick(val time: Int)
    class Start(val time: Int)
    class End(val cancelled: Boolean)

    /**
     * Register a tick listener.
     */
    fun onTick(callback: Tick.() -> Unit) = subscribe(callback)

    /**
     * Register a countdown start listener.
     */
    fun onStart(callback: Start.() -> Unit) = subscribe(callback)

    /**
     * Register a countdown end listener.
     */
    fun onEnd(callback: End.() -> Unit) = subscribe(callback)

}