package com.puppycrawl.tools.checkstyle.utils.javadocutil;
/* Config: Default
 */

/**
 *
 * {@summary This is the summary of the class.
 * <ul>
 * <li>This class does great things.</li>
 * <li>This class is a test class.</li>
 * </ul>}
 */
public class InputJavadocUtilContentOfInlineCustomTag { // ok

    /**
     * {@customTag This is a custom tag
     * {@tag A nested inline custom tag {@tag2 another nested tag}}
     * This method is a test method.}
     */
    public void method() {}

    /**
     * {@summary <!-- This is a HTML comment -->
     *  <ul>
     *   <li>Coffee</li>
     * </ul>
     * **** some stars}
     */
    public void method2() {}

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
