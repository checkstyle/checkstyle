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

    // violation 7 lines below """@see tag '#InputJavadocSeeTagOrderIncorrect2()' should be
    // placed before '#getValue()'."""
    /**
     * Invalid: local method appears before local constructor.
     *
     * @see #value
     * @see #getValue()
     * @see #InputJavadocSeeTagOrderIncorrect2()
     */
    private void methodBeforeConstructor() {
    }

    public InputJavadocSeeTagOrderIncorrect2() {
    }

    private String value;

    private String getValue() {
        return value;
    }

    // violation 6 lines below """@see tag 'OtherClass#OtherClass()' should be placed
    // before 'OtherClass#method()'."""
    /**
     * Invalid: simple class method appears before simple class constructor.
     *
     * @see OtherClass#method()
     * @see OtherClass#OtherClass()
     */
    private void simpleMemberMethodBeforeConstructor() {
    }

    static class OtherClass {
        OtherClass() {
        }

        void method() {
        }
    }
}
