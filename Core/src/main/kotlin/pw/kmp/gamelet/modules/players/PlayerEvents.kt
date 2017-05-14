package pw.kmp.gamelet.modules.players

import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * Emitted when a player joins a match.
 */
data class PlayerJoinMatchEvent(val player: Player)

/**
 * Emitted when a player leaves a match.
 */
data class PlayerLeaveMatchEvent(val player: Player)

/**
 * Emitted when a player is spawned into a match.
 */
data class PlayerSpawnEvent(val player: Player, var location: Location? = null)