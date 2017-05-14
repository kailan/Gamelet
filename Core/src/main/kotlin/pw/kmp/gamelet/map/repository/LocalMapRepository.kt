package pw.kmp.gamelet.map.repository

import java.io.File

/**
 * A repository of maps in a local folder.
 */
open class LocalMapRepository(val directory: File) : MapRepository {

    /**
     * Ensure that the folder exists.
     */
    override fun setup() {
        if (!directory.exists()) {
            directory.mkdir()
        }
    }

    /**
     * Return a list of all folders in the directory.
     */
    override fun getMaps(): Array<File> = directory.listFiles(File::isDirectory)

}