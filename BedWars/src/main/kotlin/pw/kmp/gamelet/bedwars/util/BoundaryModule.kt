package pw.kmp.gamelet.bedwars.util

import org.bukkit.event.player.PlayerMoveEvent
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.kext.Element

@GameletModule
class BoundaryModule(match: Match, config: Element) {

    init {
        val yBoundary = config.child("boundary")?.attribute("y")?.toDouble()
        if (yBoundary != null) {
            match.subscribe<PlayerMoveEvent> {
                if (to.y < yBoundary) {
                    player.damage(1000.toDouble())
                }
            }
        }
    }

}