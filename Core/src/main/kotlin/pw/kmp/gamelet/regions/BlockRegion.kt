package pw.kmp.gamelet.regions

import org.bukkit.util.Vector

/**
 * A region made of a single block.
 */
class BlockRegion(val block: Vector) : Region {

    override fun contains(point: Vector) = block.toBlockVector() == point.toBlockVector()

    override fun getRandom(): Vector = block.clone().add(Vector(Math.random(), Math.random(), Math.random()))

}