package com.puppycrawl.tools.checkstyle.javadocpropertiesgenerator;

public class InputJavadocPropertiesGeneratorCorrect {

    /**
     * The end of file token.  This is the root node for the source
     * file.  It's children are an optional package definition, zero
     * or more import statements, and one or more class or interface
     * definitions.
     **/
    @SuppressWarnings("")
    public static final @Deprecated int EOF1 = 1, EOF2 = 2;

    /**
     * '&amp;' symbol when used in a generic upper or lower bounds constrain
     * e.g&#46; {@code Comparable<T extends Serializable & CharSequence>}!
     */
    public static final int TYPE_EXTENSION_AND = 3;

    /**
     * A left curly brace (<code>{</code>).
     *
     * @noinspection HtmlTagCanBeJavadocTag
     **/
    public static final int LCURLY = 4;

    /**
     * '{@literal @}deprecated' literal in {@literal @}deprecated Javadoc tag?
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @deprecated it is deprecated method}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[3x0] : [@deprecated it is deprecated method]
     *   |--DEPRECATED_LITERAL[3x0] : [@deprecated]
     *   |--WS[3x11] : [ ]
     *   |--TEXT[3x12] : [it is deprecated method]
     * }</pre>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#deprecated">
     * Oracle Docs</a>
     */
    public static final int DEPRECATED_LITERAL = 5;
}
