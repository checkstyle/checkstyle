/*
JavadocMethod
validateThrows = true
allowedAnnotations = (default)Override
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodAnnotationPositions2 {

    /**
     * Javadoc for method with multiple annotations and modifiers.
     */
    public
    @Deprecated // c1 ends with tab and space
    // violation 2 lines above '@return tag should be present and have description.'
    // violation 4 lines below 'Expected @param tag for '<T>'.'
    // violation 3 lines below 'Expected @param tag for '<U>'.'
    // violation 2 lines below 'Expected @param tag for 'param'.'
    // violation below 'Expected @param tag for 'p2'.'
    static /*c2*/ <T, U> T m3(U param, @Deprecated int p2) {
        return null;
    }
}
