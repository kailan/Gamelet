package pw.kmp.gamelet.bedwars.teams

import org.bukkit.ChatColor
import pw.kmp.gamelet.Playerlet
import pw.kmp.gamelet.bedwars.util.toVector3D
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.modules.players.PlayerJoinMatchEvent
import pw.kmp.gamelet.modules.players.PlayerModule
import pw.kmp.gamelet.modules.teams.TeamModule
import pw.kmp.gamelet.regions.CuboidRegion
import pw.kmp.kext.Element

@GameletModule
class BWTeamModule(config: Element, match: Match, players: PlayerModule) : TeamModule(match, players) {

    init {
        config.require.child("teams").children().forEach {
            val bed = it.require.child("bed")
            val bedRegion = CuboidRegion(bed.require.attribute("bottom").toVector3D(bed), bed.require.attribute("top").toVector3D(bed))

            val color = ChatColor.valueOf(it.require.attribute("color").toUpperCase().replace(' ', '_'))
            val max = it.require.attribute("max").toInt()
            teams += BWTeam(color, bedRegion, match, max)
        }
        match.subscribe<PlayerJoinMatchEvent> {
            val team = teams.sortedBy { it.players.size }.first()
            team.addPlayer(player)
        }
    }

    fun getBWTeams() = teams.map { it as BWTeam }.toSet()
    override fun get(name: String) = super.get(name) as? BWTeam
    override fun get(player: Playerlet) = super.get(player) as? BWTeam

}