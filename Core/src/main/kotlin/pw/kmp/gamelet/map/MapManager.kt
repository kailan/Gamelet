package pw.kmp.gamelet.map

import java.io.File

/**
 * Manages the lifecycle of maps.
*/
interface MapManager {

    /**
     * Load a map from a directory.
     */
    fun loadMap(location: File)

    /**
     * Return all loaded maps.
     */
    fun getMaps(): Array<Maplet>

}