package pw.kmp.gamelet.modules.players

import org.bukkit.Location
import pw.kmp.gamelet.Playerlet

/**
 * Emitted when a player joins a match.
 */
data class PlayerJoinMatchEvent(val player: Playerlet)

/**
 * Emitted when a player leaves a match.
 */
data class PlayerLeaveMatchEvent(val player: Playerlet)

/**
 * Emitted when a player is spawned into a match.
 */
data class PlayerSpawnEvent(val player: Playerlet, var location: Location? = null)