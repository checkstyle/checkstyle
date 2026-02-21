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

public class InputJavadocMethodInClasses {
    /**
     * .
     * @param x description
     */
    // this should not cause any violation, because it is a class
    // and Javadoc method check is only for methods and constructors and interfaces
    public static class InvalidParam { /** . */ private InvalidParam() { } }
    /**
     * .
     * @param x description
     */
    // violation 2 lines above 'Unused @param tag for 'x'.'
    // violation below 'Expected @param tag for 'a'.'
    public void param_name_not_found(int a) { }
}
