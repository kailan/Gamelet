package pw.kmp.kext

import org.jdom2.located.LocatedElement

/**
 * An element in an XML document.
 */
interface Element {

    /**
     * Returns an [Element] that checks for missing data.
     */
    val require: RequiredElement
        get() = RequiredElement(this)

    /**
     * Returns the line number that this element appears on.
     */
    fun line(): Int

    /**
     * Returns the name of the element.
     * ```xml
     * <user>...</user>
     * ```
     * ```kotlin
     * user.name() // "user"
     * ```
     */
    fun name(): String

    /**
     * Returns the parent element.
     * ```xml
     * <users>
     *     <user>...</user>
     * </users>
     * ```
     * ```kotlin
     * user.parent() // <users>...</users>
     * ```
     */
    fun parent(): Element

    /**
     * Returns child elements.
     * ```xml
     * <users>
     *     <user>...</user>
     *     <user>...</user>
     * </users>
     * ```
     * ```kotlin
     * users.children() // <user>...</user>, <user>...</user>
     * ```
     */
    fun children(): Set<Element>

    /**
     * Returns a child element.
     * ```xml
     * <users>
     *     <user>...</user>
     * </users>
     * ```
     * ```kotlin
     * users.child("user") // <user>...</user>
     * ```
     * @param elementName the name of the element to fetch
     */
    fun child(elementName: String): Element?

    /**
     * Returns the text of a child element.
     * ```xml
     * <user>
     *   <name>My Name</name>
     * </user>
     * ```
     * ```kotlin
     * user.text("name") // "My Name"
     * ```
     * @param elementName the name of the element to fetch text from
     */
    fun text(elementName: String): String?

    /**
     * Returns the text of this element.
     * ```xml
     * <name>My Name</name>
     * ```
     * ```kotlin
     * name.text() // "My Name"
     * ```
     */
    fun text(): String?

    /**
     * Returns the value of an attribute.
     * ```xml
     * <user type="admin"></user>
     * ```
     * ```kotlin
     * user.attribute("type") // "admin"
     * ```
     * @param attributeName the name of the attribute to fetch
     */
    fun attribute(attributeName: String): String?

}

/**
 * An implementation of [Element] using a JDOM2 located element.
 * @property element a located JDOM2 element
 */
class KextElement internal constructor(val element: LocatedElement, val parent: KextElement?) : Element {

    override fun line(): Int {
        return element.line
    }

    override fun name(): String {
        return element.name
    }

    override fun parent() = parent ?: throw NoParentException(this)

    override fun children(): Set<Element> {
        return element.children.map {it as LocatedElement}.map {KextElement(it, this)}.toSet()
    }

    override fun child(elementName: String): Element? {
        return KextElement(element.getChild(elementName) as? LocatedElement ?: return null, this)
    }

    override fun text(elementName: String): String? {
        return element.getChildText(elementName)
    }

    override fun text(): String? {
        return element.text
    }

    override fun attribute(attributeName: String): String? {
        return element.getAttributeValue(attributeName)
    }

    override fun toString() = "<${name()}>"

}

/**
 * An implementation of [Element] that delegates calls to a parent element, checking for missing data.
 * @property element the parent element
 */
class RequiredElement internal constructor(val element: Element): Element by element {

    override fun child(elementName: String): Element = element.child(elementName) ?: throw NoChildrenException(element, elementName)
    override fun text(elementName: String): String = element.text(elementName) ?: throw NoContentException(element)
    override fun text(): String = element.text() ?: throw NoContentException(element)

    override fun attribute(attributeName: String): String = element.attribute(attributeName) ?: throw NoAttributeException(element, attributeName)

}