package pw.kmp.gamelet.map

import com.github.salomonbrys.kodein.JVMTypeToken
import com.github.salomonbrys.kodein.Kodein
import pw.kmp.gamelet.map.loader.MapLoader
import java.io.File
import java.util.logging.Logger

/**
 * Gamelet's default map manager.
 */
class SimpleMapManager(val app: Kodein, val logger: Logger) : MapManager {

    val maps = mutableSetOf<Maplet>()

    override fun loadMap(location: File) {
        val loader = getMapLoaders(location).filter { it.isCompatible() }.firstOrNull()
        if (loader != null) {
            val map: Maplet
            try {
                map = loader.load()
                maps += map
            } catch (error: Throwable) {
                logger.warning("x Loading ${location.path} failed: ${error.message}")
                return
            }
            logger.fine("+ ${map.info.name} for ${map.type}: v${map.info.version}")
        } else {
            logger.warning("x Loading ${location.path} failed: Unsupported map type?")
        }
    }

    override fun getMaps(): Array<Maplet> = maps.toTypedArray()

    private fun getMapLoaders(location: File): List<MapLoader> {
        return app.container.bindings.entries.filter { (it.key.bind.type as JVMTypeToken).type == MapLoader::class.java }.map {
            app.container.nonNullFactory(it.key).invoke(location) as MapLoader
        }.toList()
    }

}