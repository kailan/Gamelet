package pw.kmp.gamelet.bedwars.util

import org.bukkit.Location
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import pw.kmp.gamelet.bedwars.teams.BWTeamModule
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.GameletModule

@GameletModule
class MapProtectionModule(match: Match, teams: BWTeamModule) {

    init {
        val placed = mutableSetOf<Location>()
        match.subscribe<BlockPlaceEvent> {
            placed.add(block.location)
        }
        match.subscribe<BlockBreakEvent> {
            if (match.state != Match.State.RUNNING ||
                    (!placed.contains(block.location) && !teams.teams.any { it.isBed(block) })) {
                isCancelled = true
            }
        }
    }

}