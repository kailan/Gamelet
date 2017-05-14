package pw.kmp.gamelet.match

import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginLogger
import java.util.logging.LogRecord

/**
 * A logger specific to a match.
 */
class MatchLogger(val match: Match, val plugin: Plugin) : PluginLogger(plugin) {

    override fun log(record: LogRecord) {
        record.message = "[Match #${match.id}] ${record.message}"
        super.log(record)
    }

}