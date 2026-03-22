package com.puppycrawl.tools.checkstyle.javadocpropertiesgenerator;

public class InputJavadocPropertiesGeneratorInvalidTokensInsideTag {

    /**
     * A multi-line code block {@code
     * |--HTML_ELEMENT[3x0] : [<code>]
     * |--TEXT[3x6] : [int x = 10;\nint y = 20;\nint sum = x + y;]
     * |--HTML_ELEMENT_END[3x40] : [</code>]
     * }
     */
    public static final int MULTILINE_CODE_TAG = 7;
}
