/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java22
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodUnnamed {
    /**
     * Does something.
     *
     * @param input the input value
     * @return the result
     */
    public int compute(int input, int _) {
        return input * 2;
    }

    /**
     * Only an unnamed parameter.
     */
    void only(int _) {
    }

    /**
     * Two unnamed parameters.
     */
    void two(int _, int _) {
    }

    /**
     * A constructor with an unnamed parameter.
     */
    InputJavadocMethodUnnamed(int _) {
    }

    /**
     * A named parameter after an unnamed one.
     */
    void after(int _, int named) { // violation 'Expected @param tag for 'named''
    }

    /**
     * A named parameter before an unnamed one.
     *
     * @param named the named value
     */
    void before(int named, int _) {
    }

    // violation 4 lines below 'Unused @param tag for '_'.'
    /**
     * An explicit tag for an unnamed parameter is unused.
     *
     * @param _ nothing to document
     */
    void explicitTag(int _) {
    }

    /**
     * A named control missing its tag.
     */
    void control(int missing) { // violation 'Expected @param tag for 'missing''
    }
}
