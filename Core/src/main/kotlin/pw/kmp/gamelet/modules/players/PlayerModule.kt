package pw.kmp.gamelet.modules.players

import org.bukkit.plugin.Plugin
import pw.kmp.gamelet.Playerlet
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.match.event.MatchBroadcast
import pw.kmp.gamelet.modules.GameletModule

/**
 * Manages players within a match.
 */
@GameletModule
open class PlayerModule(val match: Match, plugin: Plugin) {

    val players = mutableSetOf<Playerlet>()

    init {
        match.subscribe<PlayerJoinMatchEvent> {
            plugin.server.scheduler.runTaskLater(plugin, {
                val spawn = PlayerSpawnEvent(player)
                match.notify(spawn)
                player.teleport(spawn.location ?: match.world.spawnLocation)
            }, 1)
        }
        match.subscribe<MatchBroadcast> {
            players.forEach { it.sendMessage(message) }
        }
    }

    /**
     * Adds a player to the match.
     */
    fun addPlayer(player: Playerlet) {
        if (players.contains(player)) throw IllegalArgumentException("$player can't be added to a match they are already in.")
        players += player
        player.match = match
        match.notify(PlayerJoinMatchEvent(player))
    }

    /**
     * Removes a player from the match.
     */
    fun removePlayer(player: Playerlet) {
        if (!players.contains(player)) throw IllegalArgumentException("$player can't be removed from a match they are not in.")
        match.notify(PlayerLeaveMatchEvent(player))
        players -= player
        player.match = null
    }

    /**
     * Returns whether or not a player is in the match.
     */
    fun hasPlayer(player: Playerlet) = players.contains(player)

    /**
     * Returns the amount of players in the match.
     */
    fun count() = players.size

}