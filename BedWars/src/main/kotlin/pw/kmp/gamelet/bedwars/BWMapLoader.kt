package pw.kmp.gamelet.bedwars

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import pw.kmp.gamelet.bedwars.game.BWSpawnModule
import pw.kmp.gamelet.bedwars.game.BedModule
import pw.kmp.gamelet.bedwars.game.LTSModule
import pw.kmp.gamelet.bedwars.generators.GeneratorModule
import pw.kmp.gamelet.bedwars.lobby.GameStartModule
import pw.kmp.gamelet.bedwars.teams.BWTeamModule
import pw.kmp.gamelet.bedwars.util.BoundaryModule
import pw.kmp.gamelet.bedwars.util.MapProtectionModule
import pw.kmp.gamelet.bedwars.ux.MessageModule
import pw.kmp.gamelet.map.Maplet
import pw.kmp.gamelet.map.loader.MapLoader
import pw.kmp.gamelet.modules.objective.ObjectiveModule
import pw.kmp.gamelet.modules.players.PlayerModule
import pw.kmp.kext.Element
import pw.kmp.kext.Kext
import pw.kmp.kodeinject.injectedSingleton
import java.io.File

class BWMapLoader(val location: File) : MapLoader {

    val configurationFile = File(location, "map.xml")
    lateinit var config: Element

    override fun isCompatible(): Boolean {
        try {
            return Kext.load(configurationFile).attribute("for") == "Bed Wars"
        } catch (ex: Throwable) {
            return false
        }
    }

    override fun load(): Maplet {
        config = Kext.load(configurationFile)
        if (config.require.attribute("for") != "Bed Wars") {
            throw IllegalStateException("Tried to load an invalid map.")
        }
        return Maplet("Bed Wars", this, Maplet.Info(config.require.text("name"), config.require.text("version")))
    }

    override fun getWorldLocation() = location

    override fun provideBindings() = Kodein.Module {
        bind<Element>() with instance(config)

        bind() from injectedSingleton<MessageModule>()
        bind() from injectedSingleton<MapProtectionModule>()
        bind() from injectedSingleton<BoundaryModule>()

        bind() from injectedSingleton<PlayerModule>()
        bind() from injectedSingleton<BWTeamModule>()

        bind() from injectedSingleton<BedModule>()
        bind() from injectedSingleton<BWSpawnModule>()
        bind<ObjectiveModule>() with injectedSingleton<LTSModule>()

        bind() from injectedSingleton<GeneratorModule>()

        bind() from injectedSingleton<GameStartModule>()
    }

}