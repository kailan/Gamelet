package pw.kmp.gamelet.modules.teams

import pw.kmp.gamelet.Playerlet
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.modules.players.PlayerLeaveMatchEvent
import pw.kmp.gamelet.modules.players.PlayerModule

/**
 * Manages teams within a match.
 */
@GameletModule
open class TeamModule(val match: Match, val players: PlayerModule) {

    val teams = mutableSetOf<Team>()

    init {
        match.subscribe<PlayerLeaveMatchEvent> {
            get(player)?.removePlayer(player)
        }
    }

    /**
     * Returns the team that a player is on, if any.
     */
    operator fun get(player: Playerlet) = teams.find { it.hasPlayer(player) }

    /**
     * Returns a team by name.
     */
    operator fun get(name: String) = teams.find { it.name == name }

}