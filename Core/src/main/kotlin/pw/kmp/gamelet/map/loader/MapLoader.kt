package pw.kmp.gamelet.map.loader

import com.github.salomonbrys.kodein.Kodein
import pw.kmp.gamelet.map.Maplet
import java.io.File

/**
 * Handles the reading/parsing of map information.
 * Map location should be passed into the constructor.
 */
interface MapLoader {

    /**
     * Determines whether the map is compatible with this map loader.
     */
    fun isCompatible(): Boolean

    /**
     * Parse information and create a Maplet object.
     */
    fun load(): Maplet

    /**
     * Return the location of the world files.
     */
    fun getWorldLocation(): File

    /**
     * Provide module bindings for a match.
     * Should not depend on variables outside of the "kodein" object.
     */
    fun provideBindings(): Kodein.Module

}