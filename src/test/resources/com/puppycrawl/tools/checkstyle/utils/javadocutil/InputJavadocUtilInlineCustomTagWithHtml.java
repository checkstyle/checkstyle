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
public class InputJavadocUtilInlineCustomTagWithHtml {

    /**
     * {@summary <!-- This is a HTML comment -->
     *  <ul>
     *   <li>Coffee</li>
     * </ul>
     * **** some stars}
     */
    public void method2() {}

}
