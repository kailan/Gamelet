package pw.kmp.gamelet.bedwars.game

import org.bukkit.event.entity.PlayerDeathEvent
import pw.kmp.gamelet.bedwars.teams.BWTeamModule
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.match.event.subscribe
import pw.kmp.gamelet.match.participant.Participant
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.modules.objective.ObjectiveModule

@GameletModule
class LTSModule(match: Match, val teams: BWTeamModule) : ObjectiveModule {

    init {
        match.subscribe<PlayerDeathEvent>(1, Match.State.RUNNING) {
            if (getLiveTeams().size < 2) {
                match.state = Match.State.ENDED
            }
        }
    }

    fun getLiveTeams() = teams.teams.filter { it.players.size > 0 }.toSet()

    override fun getWinner(): Participant? {
        val alive = getLiveTeams()
        if (alive.size == 1) {
            return alive.first()
        } else {
            return null
        }
    }

}