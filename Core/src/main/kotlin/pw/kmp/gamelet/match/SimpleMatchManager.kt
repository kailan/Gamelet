package pw.kmp.gamelet.match

import com.github.salomonbrys.kodein.Kodein
import org.apache.commons.io.FileUtils
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import pw.kmp.gamelet.map.Maplet
import pw.kmp.gamelet.util.EmptyChunkGenerator
import java.io.File
import java.util.logging.Logger

/**
 * Gamelet's default match manager.
 */
class SimpleMatchManager(val app: Kodein, val logger: Logger) : MatchManager {

    // A count of how many matches have been created
    // Used for assigning IDs
    var count = 0

    val matches = mutableSetOf<Match>()

    override fun createMatch(map: Maplet): Match {
        val id = ++count
        logger.info("Creating match #$id on ${map.info.name} v${map.info.version}...")

        // load map into a world
        val worldName = "match-$id"
        val worldDirectory = File(worldName)
        FileUtils.deleteDirectory(worldDirectory)
        FileUtils.copyDirectory(map.loader.getWorldLocation(), worldDirectory)
        val world = Bukkit.createWorld(WorldCreator.name(worldName).generator(EmptyChunkGenerator()).generateStructures(false))

        val match = Match(id, world, app, map)
        matches += match
        return match
    }

    override fun getMatches() = matches.toTypedArray()

    override fun findMatch() = matches.firstOrNull()

    override fun getMatch(world: World) = matches.firstOrNull { it.world == world }

}