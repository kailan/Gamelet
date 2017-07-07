package pw.kmp.gamelet.util

import com.github.salomonbrys.kodein.instance
import pw.kmp.gamelet.match.Match
import pw.kmp.gamelet.modules.players.PlayerModule
import pw.kmp.gamelet.modules.teams.Team
import pw.kmp.gamelet.modules.teams.TeamModule
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
val Match.teams: TeamModule<Team> get() = ctx.instance()