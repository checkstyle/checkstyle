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
        throw new UnsupportedOperationException(""); // ok
    }

    /** Simple, trivial case. */
    void topLevel() {
        throw new UnsupportedOperationException(""); // violation
    }

    /**
     * Example raised in issue 8117.
     *
     * @param x some input for control flow statements
     * @return 0 if there were no problems
     */
    int nestedWithinIf(int x) {
        if (x > 0) {
            throw new UnsupportedOperationException(""); // violation
        }
        if (x < 0) {
            throw new IllegalArgumentException(""); // violation
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
            throw new UnsupportedOperationException(""); // violation
        }
        if (x < 0) {
            // some comment in front
            throw new IllegalArgumentException(""); // violation
        }
    }

    /**
     * Check if-else block.
     *
     * @param x some input for control flow statements
     */
    void nestedWithinIfElse(int x) {
        if (x > 0) {
            throw new UnsupportedOperationException(""); // violation
        }
        else {
            throw new IllegalArgumentException(""); // violation
        }
    }

    /**
     * Check if-else-if block.
     *
     * @param x some input for control flow statements
     */
    void nestedWithinIfElseIf(int x) {
        if (x > 0) {
            throw new UnsupportedOperationException(""); // violation
        }
        else if (x < 0) {
            throw new IllegalArgumentException(""); // violation
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
                throw new IllegalArgumentException("0"); // violation
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
                    throw new IllegalArgumentException("0"); // violation
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
        throw new UnsupportedOperationException(""); // violation
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
            throw new RuntimeException(e); // violation
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
                throw new RuntimeException(e); // violation
            }
        }
    }

}
