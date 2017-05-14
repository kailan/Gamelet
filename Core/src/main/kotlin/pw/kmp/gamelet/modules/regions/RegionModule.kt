package pw.kmp.gamelet.modules.regions

import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.regions.Region

/**
 * A module for storing regions in a map.
 */
@GameletModule
open class RegionModule {

    // a map of IDs to regions
    val regions = mutableMapOf<String, Region>()

    /**
     * Returns a region with the given ID.
     */
    operator fun get(id: String) = regions[id]

}