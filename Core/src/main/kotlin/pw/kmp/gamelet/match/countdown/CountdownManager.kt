package pw.kmp.gamelet.match.countdown

import org.bukkit.plugin.Plugin

/**
 * Manages the scheduling of countdowns in a match.
 */
class CountdownManager(val plugin: Plugin) {

    val liveCountdowns = mutableSetOf<Countdown>()

    /**
     * Cancels all countdowns of a certain type.
     */
    inline fun <reified T : Countdown> cancel() {
        liveCountdowns.filter { it::class == T::class }.forEach { it.cancel() }
    }

    /**
     * Cancels all countdowns.
     */
    fun cancelAll() {
        liveCountdowns.forEach { it.cancel() }
    }

}