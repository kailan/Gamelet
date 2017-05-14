package pw.kmp.gamelet.match.event

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.world.WorldEvent
import org.bukkit.plugin.Plugin
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.players.PlayerModule
import pw.kmp.gamelet.playerlet

class MatchEventDispatcher(plugin: Plugin, val match: Match, val players: PlayerModule) : Listener {

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    fun unregister() {
        HandlerList.unregisterAll(this)
    }
    
    fun onEvent(event: Event) {
        if ((event is PlayerEvent && players.hasPlayer(event.player.playerlet))
                || (event is EntityEvent && event.entity is Player && players.hasPlayer((event.entity as Player).playerlet))
                || (event is WorldEvent && event.world == match.world)) {
            match.notify(event)
        }
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) = onEvent(event)

}