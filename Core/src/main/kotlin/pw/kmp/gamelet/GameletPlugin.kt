package pw.kmp.gamelet

import org.bukkit.plugin.java.JavaPlugin

/**
 * Entry point for the Bukkit plugin.
 */
class GameletPlugin : JavaPlugin() {

    lateinit var gamelet: Gamelet

    override fun onEnable() {
        gamelet = Gamelet(this)
    }

}