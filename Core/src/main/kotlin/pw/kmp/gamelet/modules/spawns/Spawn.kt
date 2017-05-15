package pw.kmp.gamelet.modules.spawns

import org.bukkit.Location

/**
 * A spawn point for players in a match.
 */
interface Spawn {

    /**
     * Returns the location of the spawn.
     */
    fun getLocation(): Location

}