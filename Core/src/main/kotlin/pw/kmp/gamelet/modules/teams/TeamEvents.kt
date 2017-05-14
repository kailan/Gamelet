package pw.kmp.gamelet.modules.teams

import org.bukkit.entity.Player

/**
 * Emitted when a player joins a team.
 */
data class PlayerJoinTeamEvent(val player: Player, val team: Team)

/**
 * Emitted when a player leaves a team.
 */
data class PlayerLeaveTeamEvent(val player: Player, val team: Team)