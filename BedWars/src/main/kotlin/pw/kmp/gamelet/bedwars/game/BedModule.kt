package pw.kmp.gamelet.bedwars.game

import org.bukkit.ChatColor
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.PlayerDeathEvent
import pw.kmp.gamelet.Playerlet
import pw.kmp.gamelet.bedwars.teams.BWTeam
import pw.kmp.gamelet.bedwars.teams.BWTeamModule
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.modules.teams.PlayerLeaveTeamEvent
import pw.kmp.gamelet.playerlet

@GameletModule
class BedModule(match: Match, teams: BWTeamModule) {

    init {
        match.subscribe<PlayerDeathEvent> {
            val player = entity.playerlet
            val team = teams[player] ?: return@subscribe

            if (!team.isBedAlive) {
                team.removePlayer(player)
                deathMessage += " ${ChatColor.RED}${ChatColor.BOLD}ELIMINATED"
            }
        }
        match.subscribe<PlayerLeaveTeamEvent> {
            if (team.players.size < 1) match.notify(TeamEliminationEvent(team as BWTeam))
        }
        match.subscribe<BlockBreakEvent> {
            teams.teams.find { it.isBed(block) && it.isBedAlive }?.let {
                val player = player.playerlet
                if (!it.hasPlayer(player)) {
                    it.isBedAlive = false
                    match.notify(BedBreakEvent(it, player))
                    block.drops.clear()
                } else {
                    player.sendMessage("${ChatColor.RED}why would you even want to break your own bed")
                    isCancelled = true
                }
            }
        }
    }

}

data class BedBreakEvent(val team: BWTeam, val breaker: Playerlet)
data class TeamEliminationEvent(val team: BWTeam)