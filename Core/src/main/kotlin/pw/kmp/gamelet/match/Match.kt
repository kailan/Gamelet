package pw.kmp.gamelet.match

import com.github.salomonbrys.kodein.JVMTypeToken
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import org.bukkit.World
import pw.kmp.gamelet.listeners.MatchEventDispatcher
import pw.kmp.gamelet.map.Maplet
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.util.logger
import pw.kmp.kodeinject.injectedSingleton
import java.util.logging.Logger

/**
 * A match with loaded modules.
 */
class Match(val id: Int, val world: World, app: Kodein, val map: Maplet) : MatchSubscribable() {

    var state = State.READY
    enum class State { READY, RUNNING, ENDED }

    // match context
    val ctx = Kodein {
        extend(app, allowOverride = true)
        import(map.loader.provideBindings())

        bind<Match>() with instance(this@Match)
        bind<Logger>(overrides = true) with injectedSingleton<MatchLogger>()
        bind<Maplet>() with instance(map)
        bind<World>() with instance(world)

        bind() from injectedSingleton<MatchEventDispatcher>()
    }

    init {
        match = this // set MatchSubscribable.match for event subscriptions

        initializeModules()
    }

    override fun toString() = "Match #$id"

    /**
     * Loops through bindings and initializes classes annotated with @GameletModule.
     */
    private fun initializeModules() {
        ctx.container.bindings.entries.filter { ((it.value.createdType as JVMTypeToken).type as Class<*>).isAnnotationPresent(GameletModule::class.java) }.forEach {
            ctx.container.nonNullProvider(it.key.bind).invoke()
            logger.fine("Match module loaded: ${it.value.createdType.simpleDispString()}")
        }
    }

}
