package pw.kmp.gamelet.event

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * A class that can handle the dispatching of data to subscribers.
 *
 * @property subscriptions an set of registered subscriptions
 */
abstract class Subscribable {

    val subscriptions: MutableSet<Subscription> = mutableSetOf()

    /**
     * Subscribe to notifications using a Subscription object.
     */
    fun <T> subscribe(subscription: Subscription) {
        subscriptions += subscription
    }

    /**
     * Subscribe to notifications of a certain type.
     */
    fun <T : Any> subscribe(type: KClass<T>, notifier: T.() -> Unit) {
        subscribe(type, 0, notifier)
    }

    /**
     * Subscribe to notifications of a certain type, specifying a priority.
     */
    fun <T : Any> subscribe(type: KClass<T>, priority: Int, notifier: T.() -> Unit) {
        subscribe<T>(TypedSubscription(type, notifier, priority))
    }

    /**
     * Dispatches a notification to subscribers.
     */
    fun notify(event: Any) {
        subscriptions.filter { it.shouldNotify(event) }.sortedBy { it.getPriority() }.forEach { it.notify(event) }
    }

}

/**
 * A subscriptions to notifications.
 */
interface Subscription {

    /**
     * Returns whether the listener should be notified of the given event.
     */
    fun shouldNotify(event: Any): Boolean

    /**
     * Notifies the listener of the given event.
     */
    fun notify(event: Any)

    /**
     * Represents the priority of the subscription.
     * Subscriptions with a lower priority are called first.
     */
    fun getPriority(): Int

}

/**
 * A subscription to notifications of a certain type.
 *
 * @property type the type of notifications to process
 * @property notifier the method called when a notification is dispatched
 */
open class TypedSubscription<T : Any>(val type: KClass<T>, val notifier: (T) -> Unit, val listenerPriority: Int = 0) : Subscription {

    override fun shouldNotify(event: Any) = event.javaClass.kotlin.isSubclassOf(type)
    override fun getPriority() = listenerPriority

    override fun notify(event: Any) {
        notifier.invoke(event as T)
    }

}