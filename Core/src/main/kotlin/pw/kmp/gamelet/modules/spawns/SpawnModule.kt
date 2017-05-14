package pw.kmp.gamelet.modules.spawns

import pw.kmp.gamelet.Playerlet
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.match.event.subscribe
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.modules.players.PlayerModule
import pw.kmp.gamelet.modules.players.PlayerSpawnEvent
import pw.kmp.gamelet.util.players

/**
 * A module for spawning players into the match.
 */
@GameletModule
open class SpawnModule(val match: Match, val players: PlayerModule) {

    init {
        match.subscribe<PlayerSpawnEvent>(arrayOf(Match.State.RUNNING)) {
            location = getSpawn(player).getLocation()
        }
        match.subscribe<PlayerSpawnEvent>(arrayOf(Match.State.READY, Match.State.ENDED)) {
            location = defaultSpawn?.getLocation()
        }
    }

    val spawns = mutableSetOf<Spawn>()
    var defaultSpawn: Spawn? = null

    /**
     * Gets a spawn suitable for the given player.
     */
    fun getSpawn(player: Playerlet): Spawn {
        if (!match.players.hasPlayer(player)) throw IllegalArgumentException("Player is not in match")
        val validSpawns = spawns.filter { it.owner == player.participant }
        if (validSpawns.isEmpty()) return defaultSpawn!!
        return validSpawns[(Math.random() * validSpawns.size).toInt()]
    }

}