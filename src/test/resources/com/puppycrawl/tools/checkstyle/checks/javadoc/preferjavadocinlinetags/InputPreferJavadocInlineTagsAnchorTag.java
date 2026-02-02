/*
PreferJavadocInlineTags
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferjavadocinlinetags;

// violation 2 lines below 'Prefer Javadoc inline tag '{@link ...}' over '<a href="#...">'.'
/**
 * See <a href="#method">method</a> for details.
 */
public class InputPreferJavadocInlineTagsAnchorTag {

    // violation 2 lines below 'Prefer Javadoc inline tag '{@link ...}' over '<a href="#...">'.'
    /**
     * Refer to <a href="#validate()">validate</a>.
     */
    public void method() {
    }

    // violation 2 lines below 'Prefer Javadoc inline tag '{@link ...}' over '<a href="#...">'.'
    /**
     * Link with multiple attributes: <a id="foo" href="#bar">bar</a>.
     */
    public void multipleAttributes() {
    }

    // violation 2 lines below 'Prefer Javadoc inline tag '{@link ...}' over '<a href="#...">'.'
    /**
     * Link with id first: <a id="foo" href="#bar">bar</a>.
     */
    public void idFirstThenHref() {
    }

    // violation 2 lines below 'Prefer Javadoc inline tag '{@link ...}' over '<a href="#...">'.'
    /**
     * Boolean attr before href: <a disabled href="#baz">baz</a>.
     */
    public void booleanAttribute() {
    }

    /**
     * Anchor without href: <a name="anchor">text</a>.
     */
    public void noHrefAttribute() {
    }

    /**
     * External link: <a href="https://example.com">link</a>.
     */
    public void externalLink() {
    }

    /** Documented method. */
    public void validate() {
    }

    /**
     * Anchor with href but no value: <a href>text</a>.
     */
    public void hrefNoValue() {
    }

    /**
     * Empty anchor with no attributes: <a>text</a>.
     */
    public void noAttributesAtAll() {
    }

    /**
     * Line break.<br/>
     */
    public void voidElement() {
    }

    /**
     * Test with non-self-closing void elements.
     * <br>Line break without closing slash.
     * <br>or<br>multiple br tags.
     * <hr>Horizontal rule.
     */
    public void voidElements() { }

    /**
     * Test with self-closing void elements.
     * <br/>Line break with closing slash.
     * <hr/>Horizontal rule.
     */
    public void selfClosingVoidElements() { }


}
