package pw.kmp.gamelet.modules.spawns

import org.bukkit.Location
import org.bukkit.World
import pw.kmp.gamelet.modules.teams.Team
import pw.kmp.gamelet.regions.Region

/**
 * A spawn point for players in a match.
 */
class Spawn(val regions: Array<Region>, val world: World, val team: Team?) {

    /**
     * Returns a random location within the spawn regions.
     */
    fun getLocation(): Location {
        val region = regions[(Math.random() * regions.size).toInt()]
        return region.getRandom().toLocation(world)
    }

}