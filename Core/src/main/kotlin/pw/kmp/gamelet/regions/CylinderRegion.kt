package pw.kmp.gamelet.regions

import org.bukkit.util.Vector

/**
 * A cylinder-shaped region.
 */
class CylinderRegion(val base: Vector, val radius: Int, val height: Int) : Region {

    override fun contains(point: Vector): Boolean {
        if (point.y < this.base.y || point.y > (this.base.y + this.height))
            return false
        return Math.pow(point.x - base.x, 2.0) + Math.pow(point.z - base.z, 2.0) <= (radius * radius)
    }

    override fun getRandom(): Vector {
        val angle = Math.random() * Math.PI * 2
        var hyp = Math.random() * Math.random()
        hyp = (if (hyp < 1) hyp else 2 - hyp) * radius

        val x = Math.cos(angle) * hyp + base.x
        val z = Math.sin(angle) * hyp + base.z
        val y = base.y + Math.random() * ((base.y + height) - base.y)
        return Vector(x, y, z)
    }

}