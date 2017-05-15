package pw.kmp.gamelet.modules.spawns

import pw.kmp.gamelet.Playerlet
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.match.event.subscribe
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.modules.players.PlayerModule
import pw.kmp.gamelet.modules.players.PlayerSpawnEvent

/**
 * A module for spawning players into the match.
 */
@GameletModule
abstract class SpawnModule(val match: Match, val players: PlayerModule) {

    init {
        match.subscribe<PlayerSpawnEvent>(arrayOf(Match.State.RUNNING)) {
            location = getSpawn(player).getLocation()
        }
        match.subscribe<PlayerSpawnEvent>(arrayOf(Match.State.READY, Match.State.ENDED)) {
            location = defaultSpawn?.getLocation()
        }
    }

    var defaultSpawn: Spawn? = null

    /**
     * Gets a spawn suitable for the given player.
     */
    abstract fun getSpawn(player: Playerlet): Spawn

}