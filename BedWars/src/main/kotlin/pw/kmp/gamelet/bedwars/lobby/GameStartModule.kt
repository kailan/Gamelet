package pw.kmp.gamelet.bedwars.lobby

import org.bukkit.ChatColor
import org.bukkit.plugin.Plugin
import pw.kmp.gamelet.bedwars.teams.BWTeamModule
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.match.countdown.Countdown
import pw.kmp.gamelet.match.event.MatchStateEvent
import pw.kmp.gamelet.match.event.subscribe
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.modules.players.PlayerJoinMatchEvent
import pw.kmp.gamelet.modules.players.PlayerModule

@GameletModule
class GameStartModule(match: Match, players: PlayerModule, teams: BWTeamModule, plugin: Plugin) {

    val countdown = Countdown(plugin)

    init {
        countdown.onEnd {
            if (!cancelled) {
                match.state = Match.State.RUNNING
            }
        }
        countdown.onTick {
            if (time % 5 == 0 || time <= 5) {
                val suffix = if (time == 1) "" else "s"
                match.broadcast("${ChatColor.YELLOW}The game starts in ${ChatColor.RED}$time ${ChatColor.YELLOW}second$suffix.")
            }
        }

        match.subscribe<PlayerJoinMatchEvent>(arrayOf(Match.State.READY)) {
            if (players.count() >= teams.teams.size && !countdown.isRunning) {
                countdown.start(15)
            }
        }
        match.subscribe<MatchStateEvent> {
            countdown.cancel()
        }
    }

}