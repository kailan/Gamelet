package pw.kmp.gamelet.modules.spawns

import pw.kmp.gamelet.Playerlet
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.modules.players.PlayerModule
import pw.kmp.gamelet.modules.players.PlayerSpawnEvent
import pw.kmp.gamelet.util.players
import pw.kmp.gamelet.util.teams

/**
 * A module for spawning players into the match.
 */
@GameletModule
open class SpawnModule(val match: Match, val players: PlayerModule) {

    init {
        match.subscribe<PlayerSpawnEvent> {
            location = getSpawn(player).getLocation()
        }
    }

    val spawns = mutableSetOf<Spawn>()
    var defaultSpawn: Spawn? = null

    /**
     * Gets a spawn suitable for the given player.
     */
    fun getSpawn(player: Playerlet): Spawn {
        if (!match.players.hasPlayer(player)) throw IllegalArgumentException("Player is not in match")
        val team = match.teams[player] ?: return defaultSpawn!!
        val validSpawns = spawns.filter { it.team == team }
        return validSpawns[(Math.random() * validSpawns.size).toInt()]
    }

}