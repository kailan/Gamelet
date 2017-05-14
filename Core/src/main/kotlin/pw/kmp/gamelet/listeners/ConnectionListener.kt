package pw.kmp.gamelet.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin
import pw.kmp.gamelet.Playerlet
import pw.kmp.gamelet.match.MatchManager
import pw.kmp.gamelet.playerlet
import pw.kmp.gamelet.util.players

/**
 * Handles server connection events.
 */
class ConnectionListener(val matches: MatchManager, val plugin: Plugin) : Listener {

    /**
     * Called when a player connects to the server and is in the game.
     */
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val match = matches.findMatch() ?: return // do nothing if there are no matches
        match.players.addPlayer(event.player.playerlet)
    }

    /**
     * Called when a player leaves the server.
     */
    @EventHandler(priority = EventPriority.HIGH)
    fun onLeave(event: PlayerQuitEvent) {
        val player = event.player.playerlet
        matches.getMatches().map { it.players }.filter { it.hasPlayer(player) }
                .firstOrNull()?.removePlayer(player)

        Playerlet.players.remove(player)
    }

}