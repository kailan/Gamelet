package pw.kmp.gamelet.match.event

import pw.kmp.gamelet.event.TypedSubscription
import pw.kmp.gamelet.match.Match
import kotlin.reflect.KClass

/**
 * Subscribe to notifications of a certain type, when the match is a specified state.
 */
inline fun <reified T : Any> Match.subscribe(states: Array<Match.State>, noinline notifier: T.() -> Unit) {
    subscribe(StatefulTypedSubscription(this, states, T::class, notifier, 0))
}

/**
 * Subscribe to notifications of a certain type, specifying a priority.
 */
inline fun <reified T : Any> Match.subscribe(states: Array<Match.State>, priority: Int, noinline notifier: T.() -> Unit) {
    subscribe(StatefulTypedSubscription(this, states, T::class, notifier, priority))
}

/**
 * A typed subscription that only fires when the match is in a certain state.
 */
class StatefulTypedSubscription<T : Any>(
        val match: Match,
        val states: Array<Match.State>,
        type: KClass<T>,
        notifier: (T) -> Unit,
        listenerPriority: Int = 0
) : TypedSubscription<T>(type, notifier, listenerPriority) {

    override fun shouldNotify(event: Any) = states.contains(match.state) && super.shouldNotify(event)

}