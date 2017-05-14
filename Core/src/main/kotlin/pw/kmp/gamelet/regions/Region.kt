package pw.kmp.gamelet.regions

import org.bukkit.util.Vector

/**
 * A region existing in the game world.
 */
interface Region {

    /**
     * Returns whether the region contains the specified point.
     */
    fun contains(point: Vector): Boolean

    /**
     * Returns a random point within the region.
     */
    fun getRandom(): Vector {
        throw UnsupportedOperationException("${this.javaClass.simpleName} doesn't support the generation of random locations.")
    }

}