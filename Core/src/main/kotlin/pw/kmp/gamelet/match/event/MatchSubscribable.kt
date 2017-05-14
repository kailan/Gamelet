package pw.kmp.gamelet.match.event

import pw.kmp.gamelet.event.Subscribable
import pw.kmp.gamelet.event.TypedSubscription
import pw.kmp.gamelet.match.Match
import kotlin.reflect.KClass

/**
 * Provides state-dependent subscription methods.
 */
open class MatchSubscribable : Subscribable() {

    // has to be initialized as it can't be passed into constructor
    lateinit var match: Match

    /**
     * Subscribe to notifications of a certain type, when the match is a specified state.
     */
    fun <T : Any> subscribe(type: KClass<T>, vararg states: Match.State, notifier: T.() -> Unit) {
        subscribe<T>(StatefulTypedSubscription(match, states as Array<Match.State>, type, notifier, 0))
    }

    /**
     * Subscribe to notifications of a certain type, specifying a priority.
     */
    fun <T : Any> subscribe(type: KClass<T>, vararg states: Match.State, priority: Int, notifier: T.() -> Unit) {
        subscribe<T>(StatefulTypedSubscription(match, states as Array<Match.State>, type, notifier, priority))
    }

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