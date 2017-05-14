package pw.kmp.kext

import org.jdom2.input.SAXBuilder
import org.jdom2.located.LocatedElement
import org.jdom2.located.LocatedJDOMFactory
import java.io.File

/**
 * A library for parsing XML easily and quickly.
 */
object Kext {

    /**
     * Loads data from an XML file.
     * @param file the file to load
     */
    fun load(file: File): Element {
        val factory = SAXBuilder()
        factory.jdomFactory = LocatedJDOMFactory()
        return KextElement(factory.build(file).rootElement as LocatedElement, null)
    }

}