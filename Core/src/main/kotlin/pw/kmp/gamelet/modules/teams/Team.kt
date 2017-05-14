package pw.kmp.gamelet.modules.teams

import org.bukkit.entity.Player
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.util.players

/**
 * A team in a match.
 */
open class Team(val name: String, val match: Match, val min: Int, val max: Int) {

    val players = mutableSetOf<Player>()

    /**
     * Adds a player to the team.
     */
    fun addPlayer(player: Player) {
        if (!match.players.hasPlayer(player)) throw IllegalArgumentException("$player isn't in $this's match.")
        if (hasPlayer(player)) throw IllegalArgumentException("$player can't be added to a team they are already in.")
        players += player
        match.notify(PlayerJoinTeamEvent(player, this))
    }

    /**
     * Removes a player from the team.
     */
    fun removePlayer(player: Player) {
        if (!hasPlayer(player)) throw IllegalArgumentException("$player can't be removed from a team they are not in.")
        match.notify(PlayerLeaveTeamEvent(player, this))
        players -= player
    }

    /**
     * Returns whether or not a player is in the team.
     */
    fun hasPlayer(player: Player) = players.contains(player)

    /**
     * Returns whether or not the team is full.
     */
    fun isFull() = max < 0 && players.size < max

    /**
     * Returns whether the team is for a single player.
     */
    fun isSolo() = max == 1

}