package pw.kmp.gamelet.modules.teams

import pw.kmp.gamelet.Playerlet
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.modules.players.PlayerLeaveMatchEvent

/**
 * Manages teams within a match.
 */
@GameletModule
abstract class TeamModule<T : Team>(val match: Match, val teams: MutableSet<T>) : MutableSet<T> by teams {

    init {
        match.subscribe<PlayerLeaveMatchEvent> {
            get(player)?.removePlayer(player)
        }
    }

    /**
     * Returns the team that a player is on, if any.
     */
    abstract operator fun get(player: Playerlet): Team?

}