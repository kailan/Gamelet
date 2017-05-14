package pw.kmp.gamelet.regions

import org.bukkit.util.Vector

/**
 * A region bound to a single point.
 */
class PointRegion(val point: Vector) : Region {

    override fun contains(point: Vector) = point == this.point

    override fun getRandom() = point

}