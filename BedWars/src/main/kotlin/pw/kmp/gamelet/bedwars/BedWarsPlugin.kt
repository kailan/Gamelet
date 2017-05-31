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
        if (command.name == "state") {
            if (args.isEmpty()) return false
            if (sender !is Player) return false

            val player = sender.playerlet
            if (player.match == null) return false
            player.match!!.state = Match.State.valueOf(args[0].toUpperCase())
        }
        return true
    }

}