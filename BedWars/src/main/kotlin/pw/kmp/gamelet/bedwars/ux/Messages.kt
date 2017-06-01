package pw.kmp.gamelet.bedwars.ux

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.teams.Team
import java.util.*

/**
 * All messages that are sent will be in this class
 *
 * This makes it easy to change the message without digging in classes
 */
class Messages {
    enum class Bed(vararg val msg: String) {
        BREAK_OWN("${ChatColor.RED}You can't break your own bed"),
        BROKEN("{0}'s${ChatColor.GOLD} bed has been broken by ${ChatColor.GRAY}{1}${ChatColor.GOLD}!");
    }

    enum class Game(vararg val msg: String) {
        STARTED("\n \n \n \n \n ", "${ChatColor.GOLD}The game has begun!"),
        FINISHED("\n \n \n \n \n ", "${ChatColor.GOLD}The game is over!"),
        FINISHED_WINNER("\n", "${ChatColor.GOLD}Congratulations to the {0}${ChatColor.GOLD} Team!");
    }

    enum class Player(vararg val msg: String) {
        DEATH_MESSAGE_ELIMINATED("${ChatColor.RED}${ChatColor.BOLD}ELIMINATED");
    }

    enum class Team(vararg val msg: String) {
        JOIN("${ChatColor.GRAY}{0}${ChatColor.GOLD} joined your team."),
        ELIMINATED("{0} ${ChatColor.GOLD}has been eliminated!");
    }
}

//TODO: Move extensions
//Put extensions here for now to not break anything

//TODO: Tidy
//TODO: Make more efficient?

/**
 * Sends a list of messages to the player
 */
fun Player.sendMessages(messages: Array<out String>, vararg values: String) {
    for(message in messages) this.sendMessage(message.replacePlaceholders(values))
}

/**
 * Sends a list of messages to the match
 */
fun Match.broadcastMessages(messages: Array<out String>, vararg values: String) {
    for (message in messages) this.broadcast(message.replacePlaceholders(values))
}

/**
 * Sends a list of messages to the team
 */
fun Team.sendMessages(messages: Array<out String>, vararg values: String) {
    for(player in this.players) for(message in messages) player.sendMessage(message.replacePlaceholders(values))
}

fun String.replacePlaceholders(values: Array<out String>) : String {
    var final: String = this
    for ((i, value) in values.withIndex()) final = final.replace("{$i}", value)

    return final
}