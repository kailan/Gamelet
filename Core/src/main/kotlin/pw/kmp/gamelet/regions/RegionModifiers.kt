package pw.kmp.gamelet.regions

import org.bukkit.util.Vector

/**
 * The inverse of a given region.
 */
class NegativeRegion(val child: Region) : Region {
    override fun contains(point: Vector) = !child.contains(point)
}

/**
 * Combines multiple regions together.
 */
class UnionRegion(val children: Array<Region>) : Region {
    override fun contains(point: Vector) = children.any { it.contains(point) }
}

/**
 * The area where multiple regions intersect.
 */
class IntersectingRegion(val children: Array<Region>) : Region {
    override fun contains(point: Vector) = children.all { it.contains(point) }
}

/**
 * Subtracts regions from a specified region.
 */
class ComplementingRegion(val original: Region, val subtractions: Array<Region>) : Region {
    override fun contains(point: Vector) = original.contains(point) && !subtractions.any { it.contains(point) }
}