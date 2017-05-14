package pw.kmp.gamelet

import org.bukkit.entity.Player
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.match.participant.Participant

/**
 * A wrapper around the Bukkit player object.
 */
class Playerlet(val bukkit: Player) : Player by bukkit {

    companion object {
        val players = mutableMapOf<Player, Playerlet>()

        operator fun get(player: Player): Playerlet {
            if (players.containsKey(player)) return players[player]!!
            val playerlet = Playerlet(player)
            players[player] = playerlet
            return playerlet
        }
    }

    var match: Match? = null
    var participant: Participant? = null

}

val Player.playerlet: Playerlet
    get() = Playerlet[this]