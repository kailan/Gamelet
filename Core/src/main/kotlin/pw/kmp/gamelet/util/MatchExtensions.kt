package pw.kmp.gamelet.util

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import org.bukkit.World
import pw.kmp.gamelet.map.Maplet
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.players.PlayerModule
import pw.kmp.gamelet.modules.teams.TeamModule
import pw.kmp.kodeinject.injectedSingleton
import java.util.logging.Logger

/**
 * Returns the logger for the match.
 */
val Match.logger: Logger get() = ctx.instance()

/**
 * Returns the player module for the match.
 * Will throw an exception if the module is not loaded.
 */
val Match.players: PlayerModule get() = ctx.instance()

/**
 * Returns the team module for the match.
 * Will throw an exception if the module is not loaded.
 */
val Match.teams: TeamModule get() = ctx.instance()