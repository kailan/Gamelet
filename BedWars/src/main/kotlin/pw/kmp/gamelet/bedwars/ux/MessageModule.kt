package pw.kmp.gamelet.bedwars.ux

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import pw.kmp.gamelet.bedwars.game.BedBreakEvent
import pw.kmp.gamelet.bedwars.game.TeamEliminationEvent
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.match.event.MatchStateEvent
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.modules.objective.ObjectiveModule
import pw.kmp.gamelet.modules.teams.PlayerJoinTeamEvent

@GameletModule
class MessageModule(match: Match, objective: ObjectiveModule) {

    init {
        match.subscribe<MatchStateEvent> {
            if (state == Match.State.RUNNING) {
                Bukkit.broadcastMessage("${ChatColor.GOLD}The game has begun!")
            } else if (state == Match.State.ENDED) {
                Bukkit.broadcastMessage("${ChatColor.GOLD}The game is over!")
                objective.getWinner()?.let { Bukkit.broadcastMessage("$it wins.") }
            }
        }
        match.subscribe<BedBreakEvent> {
            Bukkit.broadcastMessage("$team's${ChatColor.GOLD} bed has been broken by ${ChatColor.GRAY}$breaker${ChatColor.GOLD}!")
        }
        match.subscribe<TeamEliminationEvent> {
            Bukkit.broadcastMessage("$team ${ChatColor.GOLD}has been eliminated!")
        }
        match.subscribe<PlayerJoinTeamEvent> {
            Bukkit.broadcastMessage("${ChatColor.GRAY}$player${ChatColor.GOLD} joined the $team${ChatColor.GOLD}.")
        }
    }

}