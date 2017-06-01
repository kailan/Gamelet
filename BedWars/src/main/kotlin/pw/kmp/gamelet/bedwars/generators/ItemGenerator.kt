package pw.kmp.gamelet.bedwars.generators

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import pw.kmp.gamelet.bedwars.teams.BWTeam
import pw.kmp.gamelet.regions.Region

class ItemGenerator(val type: Type, val region: Region, val team: BWTeam?, val plugin: Plugin, val world: World) : BukkitRunnable() {

    enum class Type { FORGE, DIAMOND, EMERALD }

    val interval: Long = when (type) {
        ItemGenerator.Type.FORGE -> 25
        ItemGenerator.Type.DIAMOND -> 10*20
        ItemGenerator.Type.EMERALD -> 15*20
    }

    fun start() {
        runTaskTimer(plugin, 0, interval)
    }

    override fun run() {
        if (world.getNearbyEntities(region.getRandom().toLocation(world), 1.5, 1.5, 1.5).size > 32) return // entity cap

        when (type) {
            ItemGenerator.Type.FORGE -> {
                dropItem(Material.IRON_INGOT)
                if (Math.random() > 0.8) dropItem(Material.GOLD_INGOT)
            }
            ItemGenerator.Type.DIAMOND -> dropItem(Material.DIAMOND)
            ItemGenerator.Type.EMERALD -> dropItem(Material.EMERALD)
        }
    }

    private fun dropItem(material: Material) {
        if (type == Type.FORGE) {
            world.dropItemNaturally(region.getRandom().toLocation(world), ItemStack(material))
        } else {
            val item = world.dropItem(region.getRandom().toLocation(world), ItemStack(material))
            item.velocity = Vector(0, 0, 0)
        }
    }

}