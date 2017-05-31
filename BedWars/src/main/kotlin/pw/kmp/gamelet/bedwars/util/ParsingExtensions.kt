package pw.kmp.gamelet.bedwars.util

import org.bukkit.util.Vector
import pw.kmp.kext.Element
import pw.kmp.kext.XMLException

/**
 * Converts a string in the format "1,2,3" into Vector(1, 2, 3)
 */
fun String.toVector3D(element: Element): Vector {
    try {
        val split = split(",")
        return Vector(split[0].toDouble(), split[1].toDouble(), split[2].toDouble())
    } catch (ex: Throwable) {
        throw XMLException(element, "Expected a 3D vector, got '$this'.")
    }
}