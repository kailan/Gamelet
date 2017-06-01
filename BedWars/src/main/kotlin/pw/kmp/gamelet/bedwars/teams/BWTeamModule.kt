package pw.kmp.gamelet.bedwars.teams

import org.bukkit.ChatColor
import pw.kmp.gamelet.Playerlet
import pw.kmp.gamelet.bedwars.util.toVector3D
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.match.event.subscribe
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.modules.players.PlayerJoinMatchEvent
import pw.kmp.gamelet.modules.players.PlayerModule
import pw.kmp.gamelet.modules.teams.TeamModule
import pw.kmp.gamelet.regions.CuboidRegion
import pw.kmp.kext.Element

@GameletModule
class BWTeamModule(config: Element, match: Match, players: PlayerModule) : TeamModule(match) {

    val teams = mutableSetOf<BWTeam>()

    init {
        config.require.child("teams").children().forEach {
            val bed = it.require.child("bed")
            val bedRegion = CuboidRegion(bed.require.attribute("bottom").toVector3D(bed), bed.require.attribute("top").toVector3D(bed))

            val color = ChatColor.valueOf(it.require.attribute("color").toUpperCase().replace(' ', '_'))
            val max = it.require.attribute("max").toInt()
            teams += BWTeam(color, bedRegion, match, max)
        }
        match.subscribe<PlayerJoinMatchEvent>(Match.State.READY) {
            val team = teams.sortedBy { it.players.size }.first()
            team.addPlayer(player)
        }
    }

    override fun get(player: Playerlet) = teams.find { it.hasPlayer(player) }

}