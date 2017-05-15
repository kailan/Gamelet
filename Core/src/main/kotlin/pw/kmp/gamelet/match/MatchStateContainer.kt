package pw.kmp.gamelet.match

import pw.kmp.gamelet.match.event.MatchStateEvent
import kotlin.reflect.KProperty

/**
 * Handles the state of a match.
 */
class MatchStateContainer(val match: Match) {

    var state = Match.State.READY

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = state

    // Allows the following changes:
    // READY -> RUNNING
    // READY -> ENDED
    // RUNNING -> ENDED
    operator fun setValue(thisRef: Any?, property: KProperty<*>, new: Match.State) {
        val old = state
        if (old == new) return

        if (new == Match.State.READY) throw IllegalStateException("Can't set an already started match to ready.")
        if (new == Match.State.RUNNING && old == Match.State.ENDED) throw IllegalStateException("Can't start an already ended match.")

        state = new
        match.notify(MatchStateEvent(old, new))
    }

}