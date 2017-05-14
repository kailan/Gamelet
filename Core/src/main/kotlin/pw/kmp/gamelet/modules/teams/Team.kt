package pw.kmp.gamelet.modules.teams

import pw.kmp.gamelet.Playerlet
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.match.participant.Participant
import pw.kmp.gamelet.util.players

/**
 * A team in a match.
 */
open class Team(override val name: String, val match: Match, val min: Int, val max: Int) : Participant {

    override val players = mutableSetOf<Playerlet>()

    /**
     * Adds a player to the team.
     */
    fun addPlayer(player: Playerlet) {
        if (!match.players.hasPlayer(player)) throw IllegalArgumentException("$player isn't in $this's match.")
        if (hasPlayer(player)) throw IllegalArgumentException("$player can't be added to a team they are already in.")
        players += player
        player.participant = this
        match.notify(PlayerJoinTeamEvent(player, this))
    }

    /**
     * Removes a player from the team.
     */
    fun removePlayer(player: Playerlet) {
        if (!hasPlayer(player)) throw IllegalArgumentException("$player can't be removed from a team they are not in.")
        match.notify(PlayerLeaveTeamEvent(player, this))
        players -= player
        player.participant = this
    }

    /**
     * Returns whether or not a player is in the team.
     */
    fun hasPlayer(player: Playerlet) = players.contains(player)

    /**
     * Returns whether or not the team is full.
     */
    fun isFull() = max < 0 && players.size < max

}