package pw.kmp.gamelet.bedwars.game

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.ItemStack
import pw.kmp.gamelet.bedwars.teams.BWTeamModule
import pw.kmp.gamelet.bedwars.util.toVector3D
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.match.event.MatchStateEvent
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.modules.players.PlayerModule
import pw.kmp.gamelet.modules.players.PlayerSpawnEvent
import pw.kmp.gamelet.playerlet
import pw.kmp.kext.Element

@GameletModule
class BWSpawnModule(match: Match, players: PlayerModule, teams: BWTeamModule, world: World, config: Element) {

    init {
        val defaultSpawn = config.child("spawn")?.let {it.require.attribute("location").toVector3D(it)}?.toLocation(world) ?: world.spawnLocation

        match.subscribe<MatchStateEvent> {
            players.players.forEach {
                val spawn = PlayerSpawnEvent(it)
                match.notify(spawn)
                it.teleport(spawn.location)
            }
        }
        match.subscribe<PlayerRespawnEvent> {
            val player = player.playerlet
            val spawn = PlayerSpawnEvent(player)
            match.notify(spawn)
            respawnLocation = spawn.location ?: world.spawnLocation
        }
        match.subscribe<PlayerSpawnEvent> {
            if (teams[player] != null && match.state == Match.State.RUNNING) {
                player.reset()
                location = teams[player]?.bed?.getRandom()?.toLocation(world)?.add(0.toDouble(), 1.toDouble(), 0.toDouble())
                // TODO: NUKE
                player.inventory.addItem(ItemStack(Material.STONE, 1))
                player.inventory.addItem(ItemStack(Material.WOOD_PICKAXE, 1))
                player.inventory.chestplate = ItemStack(Material.LEATHER_CHESTPLATE, 1)
                player.inventory.addItem(ItemStack(Material.BOW, 1))
                player.inventory.addItem(ItemStack(Material.ARROW, 16))
                player.inventory.addItem(ItemStack(Material.COOKED_BEEF, 16))
                player.inventory.addItem(ItemStack(Material.STONE, 64))
                player.inventory.addItem(ItemStack(Material.STONE, 64))
                player.updateInventory()
                // NUKE
            } else {
                player.reset(GameMode.SPECTATOR)
                location = defaultSpawn
            }
        }
    }

}