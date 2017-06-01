package pw.kmp.gamelet.bedwars

import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.factory
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import pw.kmp.gamelet.GameletPlugin
import pw.kmp.gamelet.map.loader.MapLoader
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.playerlet
import java.io.File

class BedWarsPlugin : JavaPlugin() {

    override fun onEnable() {
        (server.pluginManager.getPlugin("Gamelet") as GameletPlugin).gamelet.app.addConfig {
            bind<MapLoader>("Bed Wars") with factory { f: File -> BWMapLoader(f) }
        }
    }

    // TODO: remove
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (command.name == "debug") {
            if (sender !is Player) return false
            val match = sender.playerlet.match ?: return false

            if (!args.isEmpty()) {
                try {
                    match.state = Match.State.valueOf(args[0].toUpperCase())
                } catch (ex: Exception) {
                    sender.sendMessage("Unable to update state: ${ex.message}")
                }
            } else {
                sender.sendMessage("$match:")
                sender.sendMessage(" state: ${match.state}")
                sender.sendMessage(" registered bindings: ${match.ctx.container.bindings.size}")
                sender.sendMessage(" subscriptions: ${match.subscriptions.size}")
            }
        }
        return true
    }

}