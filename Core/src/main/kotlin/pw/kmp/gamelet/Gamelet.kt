package pw.kmp.gamelet

import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import pw.kmp.gamelet.listeners.ConnectionListener
import pw.kmp.gamelet.map.MapManager
import pw.kmp.gamelet.map.SimpleMapManager
import pw.kmp.gamelet.map.repository.LocalMapRepository
import pw.kmp.gamelet.map.repository.MapRepository
import pw.kmp.gamelet.match.MatchManager
import pw.kmp.gamelet.match.SimpleMatchManager
import pw.kmp.kodeinject.injectedSingleton
import java.io.File
import java.util.logging.Logger

/**
 * The main class for Gamelet.
 * Handles dependency management and setup methods.
 */
class Gamelet(plugin: JavaPlugin) {

    val app = ConfigurableKodein()

    init {
        app.addConfig {
            bind<Kodein>() with provider { kodein }
            bind<Plugin>() with instance(plugin)
            bind<Logger>() with provider { instance<Plugin>().logger }

            // Map management
            bind<MapManager>() with injectedSingleton<SimpleMapManager>()
            bind<MapRepository>() with singleton { LocalMapRepository(File("maps")) }

            // Match management
            bind<MatchManager>() with injectedSingleton<SimpleMatchManager>()
        }

        // delay until server is ready
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, { ready() })
    }

    /**
     * Called when the server is ready and plugins have been enabled.
     */
    private fun ready() {
        val plugin: Plugin = app.instance()

        // register events
        Bukkit.getPluginManager().registerEvents(ConnectionListener(app.instance(), plugin), plugin)

        // load maps
        loadMaps()

        // create a match
        val matches: MatchManager = app.instance()
        val maps: MapManager = app.instance()
        if (maps.getMaps().isNotEmpty()) {
            val map = maps.getMaps()[Math.floor(Math.random() * maps.getMaps().size).toInt()]
            matches.createMatch(map)
        }
    }

    /**
     * Loads all maps from the configured repository.
     */
    private fun loadMaps() {
        val logger: Logger = app.instance()
        val repo: MapRepository = app.instance()
        val maps: MapManager = app.instance()

        repo.setup() // some repository types have requirements before being used

        logger.info("Loading maps...")
        val time = System.currentTimeMillis()
        repo.getMaps().forEach { maps.loadMap(it) }
        logger.info("Loaded ${maps.getMaps().size} maps in ${System.currentTimeMillis() - time}ms.")
    }
}