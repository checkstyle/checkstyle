/*
JavadocMethod
allowInlineReturn = (default)false
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodDoNotAllowInlineReturn {

    /**
     * {@return the foo}
     */
    public int getFoo() { return 0; }
    // violation above, '@return tag should be present and have description.'

    /**
     * Returns the bar
     * @return the bar
     */
    public int getBar() { return 0; }

    /**
     * Returns the fiz
     */
    public int getFiz() { return 0; }
    // violation above, '@return tag should be present and have description.'

    /**
     * Returns the baz
     * @see "getFoo"
     */
    public int getBaz() { return 0; }
    // violation above, '@return tag should be present and have description.'
}
