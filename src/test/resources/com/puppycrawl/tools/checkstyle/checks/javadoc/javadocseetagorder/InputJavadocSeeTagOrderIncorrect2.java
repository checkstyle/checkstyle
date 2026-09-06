/*
JavadocSeeTagOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocseetagorder;

public class InputJavadocSeeTagOrderIncorrect2 {

    // violation 12 lines below """@see tag '#field1' should be
    // placed before '#beta()'."""
    // violation 11 lines below """@see tag '#field2' should be
    // placed before '#beta()'."""
    /**
     * Invalid: two callables of equal structural rank are followed by two
     * fields. Both fields are reported against the later callable, proving
     * the running maximum reference is updated even when a new reference
     * ties the current one instead of strictly exceeding it.
     *
     * @see #alpha()
     * @see #beta()
     * @see #field1
     * @see #field2
     */
    private void tiedMaximumIsUpdated() {
    }

    private void alpha() {
    }

    private void beta() {
    }

    private String field1;

    private String field2;
}
