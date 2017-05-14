package pw.kmp.gamelet.map

import pw.kmp.gamelet.map.loader.MapLoader

/**
 * A map available for use in a match.
 */
open class Maplet(val type: String, val loader: MapLoader, val info: Info) {

    data class Info(val name: String, val version: String)

}