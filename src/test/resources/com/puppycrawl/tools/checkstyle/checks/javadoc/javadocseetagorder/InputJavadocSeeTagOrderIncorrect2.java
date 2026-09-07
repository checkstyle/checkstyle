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

    // violation 9 lines below """@see tag '#name' should be placed
    // before '#age'."""
    /**
     * Invalid: the same field is referenced twice, separated by an
     * unrelated field reference, which still breaks the grouping of
     * the two overloaded-looking references to the same field.
     *
     * @see #name
     * @see #age
     * @see #name
     */
    private void duplicateFieldReference() {
    }

    private String name;

    private int age;

    // violation 9 lines below """@see tag 'java.awt.List' should be
    // placed before 'java.util.Map'."""
    /**
     * Invalid: java.util.List and java.awt.List share the same simple
     * name "List", so an unrelated type reference between them still
     * breaks their grouping.
     *
     * @see java.util.List
     * @see java.util.Map
     * @see java.awt.List
     */
    private void ambiguousSimpleTypeName() {
    }
}
