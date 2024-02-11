/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodThrowsDetection {

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

    /**
     * Nesting inside 2 layers of if statements.
     *
     * @param x some input for control flow statements
     */
    void doubleNestedIf(int x) {
        if (x >= 0) {
            if (x <= 0) {
                // violation below 'Expected @throws tag for 'IllegalArgumentException'.'
                throw new IllegalArgumentException("0");
            }
        }
    }

    /**
     * Nesting inside 3 layers of if statements.
     *
     * @param x some input for control flow statements
     */
    void tripleNestedIf(int x) {
        if (x >= 0)
            if (x <= 0)
                if (x == 0)
                    // violation below 'Expected @throws tag for 'IllegalArgumentException'.'
                    throw new IllegalArgumentException("0");
    }

    /**
     * Throw outside but after if statement.
     *
     * @param x some input for control flow statements
     */
    void throwAfterIf(int x) {
        if (x > 0) {
            x = 0;
        }
        // violation below 'Expected @throws tag for 'UnsupportedOperationException'.'
        throw new UnsupportedOperationException("");
    }

    /**
     * Throw in catch but after if statement.
     *
     * @param x some input for control flow statements
     */
    void tryCatchAfterIf(int x) {
        if (x == 0) {
            return;
        }
        try {
            System.out.println("foo");
        } catch (Exception e) {
            // violation below 'Expected @throws tag for 'RuntimeException'.'
            throw new RuntimeException(e);
        }
    }

    /**
     * Throw in try/catch inside if statement.
     * @param x some input for control flow statements
     */
    void tryCatchWithinIf(int x) {
        if (x == 0) {
            try {
                System.out.println("foo");
            } catch (Exception e) {
                // violation below 'Expected @throws tag for 'RuntimeException'.'
                throw new RuntimeException(e);
            }
        }
    }

}
