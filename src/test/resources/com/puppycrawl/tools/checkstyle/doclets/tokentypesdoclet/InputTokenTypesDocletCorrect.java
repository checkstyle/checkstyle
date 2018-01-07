package com.puppycrawl.tools.checkstyle.doclets.tokentypesdoclet;

import com.puppycrawl.tools.checkstyle.grammars.GeneratedJavaTokenTypes;

public class InputTokenTypesDocletCorrect {

    /**
     * The end of file token.  This is the root node for the source
     * file.  It's children are an optional package definition, zero
     * or more import statements, and one or more class or interface
     * definitions.
     **/
    public static final int EOF1 = 1, EOF2 = 2;

    /**
     * '&amp;' symbol when used in a generic upper or lower bounds constrain
     * e.g&#46; {@code Comparable<T extends Serializable & CharSequence>}.
     */
    public static final int TYPE_EXTENSION_AND = GeneratedJavaTokenTypes.TYPE_EXTENSION_AND;

    /**
     * A left curly brace (<code>{</code>).
     *
     * @noinspection HtmlTagCanBeJavadocTag
     **/
    public static final int LCURLY = GeneratedJavaTokenTypes.LCURLY;
}
