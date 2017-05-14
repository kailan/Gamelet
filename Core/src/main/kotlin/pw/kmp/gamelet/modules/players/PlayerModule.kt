package pw.kmp.gamelet.modules.players

import org.bukkit.entity.Player
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.GameletModule

/**
 * Manages players within a match.
 */
@GameletModule
open class PlayerModule(val match: Match) {

    val players = mutableSetOf<Player>()

    init {
        match.subscribe<PlayerJoinMatchEvent> {
            val spawn = PlayerSpawnEvent(player)
            match.notify(spawn)
            player.teleport(spawn.location)
        }
    }

    /**
     * Adds a player to the match.
     */
    fun addPlayer(player: Player) {
        if (players.contains(player)) throw IllegalArgumentException("$player can't be added to a match they are already in.")
        players += player
        match.notify(PlayerJoinMatchEvent(player))
    }

    /**
     * Removes a player from the match.
     */
    fun removePlayer(player: Player) {
        if (!players.contains(player)) throw IllegalArgumentException("$player can't be removed from a match they are not in.")
        match.notify(PlayerLeaveMatchEvent(player))
        players -= player
    }

    /**
     * Returns whether or not a player is in the match.
     */
    fun hasPlayer(player: Player) = players.contains(player)

}