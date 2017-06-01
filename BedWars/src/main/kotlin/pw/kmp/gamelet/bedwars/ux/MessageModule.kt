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
                match.broadcastMessages(Messages.Game.STARTED.msg)
            } else if (state == Match.State.ENDED) {
                match.broadcastMessages(Messages.Game.FINISHED.msg)
                objective.getWinner()?.let { match.broadcastMessages(Messages.Game.FINISHED_WINNER.msg, it.toString()) }
            }
        }
        match.subscribe<BedBreakEvent> {
            match.broadcastMessages(Messages.Bed.BROKEN.msg, team.toString(), breaker.toString())
        }
        match.subscribe<TeamEliminationEvent> {
            match.broadcastMessages(Messages.Team.ELIMINATED.msg, team.toString())
        }
        match.subscribe<PlayerJoinTeamEvent> {
            team.sendMessages(Messages.Team.JOIN.msg, player.displayName)
        }
    }

}