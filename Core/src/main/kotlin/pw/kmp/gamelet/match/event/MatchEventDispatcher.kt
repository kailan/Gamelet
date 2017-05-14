package pw.kmp.gamelet.match.event

import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.world.WorldEvent
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.RegisteredListener
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.players.PlayerModule

class MatchEventDispatcher(plugin: Plugin, val match: Match, val players: PlayerModule) : Listener {

    init {
        val listener = RegisteredListener(this, EventExecutor { l, e -> onEvent(e) }, EventPriority.NORMAL, plugin, false)
        HandlerList.getHandlerLists().forEach { it.register(listener) }
    }

    fun unregister() {
        HandlerList.unregisterAll(this)
    }
    
    fun onEvent(event: Event) {
        if ((event is PlayerEvent && players.hasPlayer(event.player))
                || (event is WorldEvent && event.world == match.world)) {
            match.notify(event)
        }
    }

}