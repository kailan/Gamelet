package pw.kmp.gamelet.match

import org.bukkit.World
import pw.kmp.gamelet.map.Maplet

/**
 * Manages the lifecycle of matches.
 */
interface MatchManager {

    /**
     * Creates a new match on the given map.
     */
    fun createMatch(map: Maplet): Match

    /**
     * Returns all of the ongoing matches.
     */
    fun getMatches(): Array<Match>

    /**
     * Returns an ongoing match that is suitable to join.
     */
    fun findMatch(): Match?

    /**
     * Returns an ongoing match (if any) in the specified world.
     */
    fun getMatch(world: World): Match?

}
