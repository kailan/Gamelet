package pw.kmp.gamelet.regions

import org.bukkit.util.Vector

/**
 * A cuboid-shaped region.
 */
class CuboidRegion(pos1: Vector, pos2: Vector) : Region {

    val minimum: Vector = Vector.getMinimum(pos1, pos2)
    val maximum: Vector = Vector.getMaximum(pos1, pos2)

    override fun contains(point: Vector) = point.isInAABB(minimum, maximum)

    override fun getRandom(): Vector {
        val x = randomRange(minimum.x, maximum.x)
        val y = randomRange(minimum.y, maximum.y)
        val z = randomRange(minimum.z, maximum.z)
        return Vector(x, y, z)
    }

    private fun randomRange(min: Double, max: Double) = (max - min) * Math.random() + min

}