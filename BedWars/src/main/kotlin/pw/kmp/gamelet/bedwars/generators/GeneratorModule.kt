package pw.kmp.gamelet.bedwars.generators

import org.bukkit.World
import org.bukkit.plugin.Plugin
import pw.kmp.gamelet.bedwars.teams.BWTeamModule
import pw.kmp.gamelet.bedwars.util.toVector3D
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.match.event.MatchStateEvent
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.regions.PointRegion
import pw.kmp.kext.Element
import pw.kmp.kext.XMLException

@GameletModule
class GeneratorModule(match: Match, config: Element, teams: BWTeamModule, plugin: Plugin, world: World) {

    val generators = mutableSetOf<ItemGenerator>()

    init {
        config.child("generators")?.children()?.forEach {
            val type = try {
                ItemGenerator.Type.valueOf(it.name().toUpperCase())
            } catch (ex: IllegalArgumentException) {
                throw XMLException(it, "Unknown generator type: ${it.name()}.")
            }
            val team = it.attribute("for")?.let { teams[it] }
            val location = it.require.attribute("location").toVector3D(it)

            generators += ItemGenerator(type, PointRegion(location), team, plugin, world)
        }

        match.subscribe<MatchStateEvent> {
            if (state == Match.State.RUNNING) {
                generators.forEach { it.start() }
            } else {
                generators.forEach { it.cancel() }
            }
        }
    }

}