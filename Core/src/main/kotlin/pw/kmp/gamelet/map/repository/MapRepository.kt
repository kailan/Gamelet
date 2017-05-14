package pw.kmp.gamelet.map.repository

import java.io.File

/**
 * A map repository allows for the loading of maps from a given location (over the web, from disk, etc.).
 */
interface MapRepository {

    /**
     * Prepare the repository, ensuring the maps are on the local file system.
     * e.g cloning a repository from the web
     */
    fun setup()

    /**
     * Return an array of map directories.
     */
    fun getMaps(): Array<File>

}