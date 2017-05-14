package pw.kmp.kext

open class XMLException(val element: Element, val text: String) : Exception() {

    override val message: String?
        get() = "$element on line ${element.line()}: $text"

}

class NoParentException(element: Element) : XMLException(element, "Expected a parent element.")
class NoChildrenException(element: Element, childName: String? = null)
    : XMLException(element, "Expected a child element${if (childName != null) " ($childName)" else ""}.")

class NoContentException(element: Element) : XMLException(element, "Expected content.")
class NoAttributeException(element: Element, attributeName: String) : XMLException(element, "Expected a $attributeName attribute.")