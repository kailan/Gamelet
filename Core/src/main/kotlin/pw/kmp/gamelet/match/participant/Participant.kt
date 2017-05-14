package pw.kmp.gamelet.match.participant

import org.bukkit.entity.Player

/**
 * A participant in a match. Can be a single player or a team.
 */
interface Participant {

    val name: String
    val players: Set<Player>

}