package com.puppycrawl.tools.checkstyle.javadocpropertiesgenerator;

public class InputJavadocPropertiesGeneratorCorrect2 {

    // note: &#064; is the html escape for '@',
    // used here to avoid confusing the javadoc tool
    /**
     * An annotation of a package, type, field, parameter or variable.
     * An annotation may occur anywhere modifiers occur (it is a
     * type of modifier) and may also occur prior to a package definition.
     * The notable children are: The annotation name and either a single
     * default annotation value or a sequence of name value pairs.
     * Annotation values may also be annotations themselves.
     */
    public static final @Deprecated int EOF1 = 1, EOF2 = 2;
}
