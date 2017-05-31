package pw.kmp.gamelet.bedwars.teams

import org.bukkit.ChatColor
import org.bukkit.block.Block
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.teams.Team
import pw.kmp.gamelet.regions.Region

class BWTeam(val color: ChatColor, val bed: Region, match: Match, max: Int)
    : Team(generateName(color), match, 0, max) {

    var isBedAlive = true

    fun isBed(block: Block) = bed.contains(block.location.toVector())

    override fun toString() = "$color$name"

    companion object {
        private fun generateName(color: ChatColor): String {
            return color.name.toLowerCase().replace('_', ' ').capitalize() + " Team"
        }
    }

}