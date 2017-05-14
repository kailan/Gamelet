package pw.kmp.gamelet.match.event

import pw.kmp.gamelet.match.Match

/**
 * Called when the state of the match changes.
 */
data class MatchStateEvent(val oldState: Match.State, val state: Match.State)
