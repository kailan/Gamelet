package pw.kmp.gamelet.modules.info

import java.util.*

/**
 * Provides author and contributor information for maps.
 */
interface AuthorModule {

    /**
     * Get the authors and contributors for the map.
     */
    fun getAuthors(): Array<Author>

    data class Author(val name: String?, val uuid: UUID?, val role: String?)

}
