/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodInReferenceTest {
    /**
     * @param x description
     */
    public int invalid_param;

    /**
     * @param x description
     */
    // violation 2 lines above 'Unused @param tag for 'x'.'
    public class InvalidParam {
        public InvalidParam() { }
    }

    /**
     * @param x description
     */
    // violation 2 lines above 'Unused @param tag for 'x'.'
    // violation below 'Expected @param tag for 'a'.'
    public void param_name_not_found(int a) { }

    /**
     * @param <X> description
     */
    // violation 2 lines above 'Unused @param tag for '<X>'.'
    public class typaram_name_not_found {
        public typaram_name_not_found() { }
    }

    /**
     * @see Object#tooStrong()
     */
    public void ref_not_found() { }

    /**
     * @return x description
     */
    public int invalid_return;
}
