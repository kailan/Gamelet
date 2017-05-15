package pw.kmp.gamelet.match.countdown

import org.bukkit.scheduler.BukkitRunnable

/**
 * A single countdown.
 */
abstract class Countdown : BukkitRunnable() {

    var manager: CountdownManager? = null
    var time: Int = -1
    var ended = false

    /**
     * Starts the countdown at the specified time.
     *
     * @param manager the countdown manager
     * @param time the start time
     */
    fun start(manager: CountdownManager, time: Int) {
        if (this.manager != null) throw IllegalStateException("Countdown cannot be started more than once.")
        if (time < 1) throw IllegalArgumentException("Countdown must have a positive start time.")

        this.manager = manager
        this.time = time
        manager.liveCountdowns += this
        runTaskTimer(manager.plugin, 0, 20)
    }

    private fun cleanup() {
        ended = true
        super.cancel()
        manager!!.liveCountdowns -= this
    }

    /**
     * Cancels the countdown prematurely.
     */
    override fun cancel() {
        if (ended) return
        onCancel()
        cleanup()
    }

    override fun run() {
        if (time == 0) {
            onFinish()
            cleanup()
        } else {
            onTick()
            time--
        }
    }

    /**
     * Called when the countdown starts.
     */
    abstract fun onStart()

    /**
     * Called when the countdown ticks, every second.
     *
     * @param timeLeft the amount of time left
     */
    abstract fun onTick()

    /**
     * Called when the countdown finishes.
     */
    abstract fun onFinish()

    /**
     * Called when the countdown is cancelled prematurely.
     */
    abstract fun onCancel()

}