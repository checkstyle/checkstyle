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
public class InputJavadocUtilTest_ContentOfInlineCustomTag { // ok

    /**
     * {@customTag This is a custom tag
     * {@tag A nested inline custom tag {@tag2 another nested tag}}
     * This method is a test method.}
     */
    public void method() {}
}
