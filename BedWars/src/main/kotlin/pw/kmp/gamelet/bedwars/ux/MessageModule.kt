package pw.kmp.gamelet.bedwars.ux

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
                match.broadcast("${ChatColor.GOLD}The game has begun!")
            } else if (state == Match.State.ENDED) {
                match.broadcast("${ChatColor.GOLD}The game is over!")
                objective.getWinner()?.let { match.broadcast("$it wins.") }
            }
        }
        match.subscribe<BedBreakEvent> {
            match.broadcast("$team's${ChatColor.GOLD} bed has been broken by ${ChatColor.GRAY}$breaker${ChatColor.GOLD}!")
        }
        match.subscribe<TeamEliminationEvent> {
            match.broadcast("$team ${ChatColor.GOLD}has been eliminated!")
        }
        match.subscribe<PlayerJoinTeamEvent> {
            match.broadcast("${ChatColor.GRAY}$player${ChatColor.GOLD} joined the $team${ChatColor.GOLD}.")
        }
    }

}