package pw.kmp.gamelet.modules.teams

import org.bukkit.entity.Player
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.modules.players.PlayerJoinMatchEvent
import pw.kmp.gamelet.modules.players.PlayerLeaveMatchEvent
import pw.kmp.gamelet.modules.players.PlayerModule

/**
 * Manages teams within a match.
 */
@GameletModule
open class TeamModule(val match: Match, val players: PlayerModule) {

    val teams = mutableSetOf<Team>()

    init {
        match.subscribe(PlayerJoinMatchEvent::class, -1) {
            teams.filter { !it.isFull() }.sortedBy { it.players.size }.firstOrNull()?.addPlayer(player) // TODO: join commands
        }
        match.subscribe(PlayerLeaveMatchEvent::class) {
            get(player)?.removePlayer(player)
        }
    }

    /**
     * Returns the team that a player is on, if any.
     */
    operator fun get(player: Player) = teams.find { it.hasPlayer(player) }

    /**
     * Returns a team by name.
     */
    operator fun get(name: String) = teams.find { it.name == name }

}