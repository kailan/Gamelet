package pw.kmp.gamelet

import org.bukkit.GameMode
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

    /**
     * Resets player attributes such as inventory and gamemode.
     */
    fun reset(gamemode: GameMode = GameMode.SURVIVAL) {
        // gamemode
        gameMode = gamemode

        // inventory
        inventory.clear()
        updateInventory()

        // health
        health = maxHealth
        saturation = 20.toFloat()
        fallDistance = 0.toFloat()
        exhaustion = 0.toFloat()

        // effects
        activePotionEffects.forEach { removePotionEffect(it.type) }
    }

    override fun toString(): String = name

}

val Player.playerlet: Playerlet
    get() = Playerlet[this]