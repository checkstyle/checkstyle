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

public class InputJavadocMethodThrowsDetectionOne {

    void noJavadoc() {
        // no javadoc, no violations
        throw new UnsupportedOperationException("");
    }

    /** Simple, trivial case. */
    void topLevel() {
        // violation below 'Expected @throws tag for 'UnsupportedOperationException'.'
        throw new UnsupportedOperationException("");
    }

    /**
     * Example raised in issue 8117.
     *
     * @param x some input for control flow statements
     * @return 0 if there were no problems
     */
    int nestedWithinIf(int x) {
        if (x > 0) {
            // violation below 'Expected @throws tag for 'UnsupportedOperationException'.'
            throw new UnsupportedOperationException("");
        }
        if (x < 0) {
            // violation below 'Expected @throws tag for 'IllegalArgumentException'.'
            throw new IllegalArgumentException("");
        }
        return 0;
    }

    /**
     * Some regression cases have comments in front.
     *
     * @param x some input for control flow statements
     */
    void nestedWithinIfLeadingComment(int x) {
        if (x > 0) {
            // some comment in front
            // violation below 'Expected @throws tag for 'UnsupportedOperationException'.'
            throw new UnsupportedOperationException("");
        }
        if (x < 0) {
            // some comment in front
            // violation below 'Expected @throws tag for 'IllegalArgumentException'.'
            throw new IllegalArgumentException("");
        }
    }

    /**
     * Check if-else block.
     *
     * @param x some input for control flow statements
     */
    void nestedWithinIfElse(int x) {
        if (x > 0) {
            // violation below 'Expected @throws tag for 'UnsupportedOperationException'.'
            throw new UnsupportedOperationException("");
        }
        else {
            // violation below 'Expected @throws tag for 'IllegalArgumentException'.'
            throw new IllegalArgumentException("");
        }
    }

    /**
     * Check if-else-if block.
     *
     * @param x some input for control flow statements
     */
    void nestedWithinIfElseIf(int x) {
        if (x > 0) {
            // violation below 'Expected @throws tag for 'UnsupportedOperationException'.'
            throw new UnsupportedOperationException("");
        }
        else if (x < 0) {
            // violation below 'Expected @throws tag for 'IllegalArgumentException'.'
            throw new IllegalArgumentException("");
        }
    }

}
