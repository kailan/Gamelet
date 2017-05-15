package pw.kmp.gamelet.match

import com.github.salomonbrys.kodein.JVMTypeToken
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import org.bukkit.World
import pw.kmp.gamelet.event.Subscribable
import pw.kmp.gamelet.map.Maplet
import pw.kmp.gamelet.match.countdown.CountdownManager
import pw.kmp.gamelet.match.event.MatchEventDispatcher
import pw.kmp.gamelet.modules.GameletModule
import pw.kmp.gamelet.util.logger
import pw.kmp.kodeinject.injectedSingleton
import java.util.logging.Logger

/**
 * A match with loaded modules.
 */
class Match(val id: Int, val world: World, app: Kodein, val map: Maplet) : Subscribable() {

    enum class State { READY, RUNNING, ENDED }
    var state: State by MatchStateContainer(this)

    // match context
    val ctx = Kodein {
        extend(app, allowOverride = true)
        import(map.loader.provideBindings())

        bind<Match>() with instance(this@Match)
        bind<Logger>(overrides = true) with injectedSingleton<MatchLogger>()
        bind<Maplet>() with instance(map)
        bind<World>() with instance(world)

        bind() from injectedSingleton<CountdownManager>()
        bind() from injectedSingleton<MatchEventDispatcher>()
    }

    init {
        initializeModules()
        ctx.instance<MatchEventDispatcher>() // initialise event dispatcher
    }

    fun cleanup() {
        state = State.ENDED
        ctx.instance<MatchEventDispatcher>().unregister()
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
