package com.puppycrawl.tools.checkstyle.utils.javadocutil;
/* Config: Default
 */

public class InputJavadocUtilInlineCustomTagWithNestedTags { // ok

    /**
     * {@customTag This is a custom tag
     * {@tag A nested inline custom tag {@tag2 another nested tag}}
     * This method is a test method.}
     */
    public void method() {}

    /**
     * {@summary first sentence is normally the summary.
     * Use of html tags:
     * {@throws error}
     * <ul>
     * <li>Item one.</li>
     * <li>Item two.</li>
     * </ul>}
     */
    public void method3() {}
}
