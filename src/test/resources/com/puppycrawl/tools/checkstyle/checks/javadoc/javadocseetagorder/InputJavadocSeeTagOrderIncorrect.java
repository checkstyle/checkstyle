/*
JavadocSeeTagOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocseetagorder;

import java.util.List;

public class InputJavadocSeeTagOrderIncorrect {

    // violation 6 lines below """@see tag '#InputJavadocSeeTagOrderIncorrect()' should be
    // placed before '#InputJavadocSeeTagOrderIncorrect(String)'."""
    /**
     * Invalid: overloaded constructors are not in telescoping order.
     *
     * @see #InputJavadocSeeTagOrderIncorrect(String)
     * @see #InputJavadocSeeTagOrderIncorrect()
     * @see #InputJavadocSeeTagOrderIncorrect(String, int)
     */
    public InputJavadocSeeTagOrderIncorrect(String value) {
    }

    // violation 7 lines below """@see tag '#getName(String)' should be placed before
    // '#setName(String)'."""
    /**
     * Invalid: overloaded methods with the same name are not grouped together.
     *
     * @see #getName()
     * @see #setName(String)
     * @see #getName(String)
     */
    public void getName() {
    }

    // violation 6 lines below """@see tag '#setName()' should be placed before
    // '#setName(String)'."""
    /**
     * Invalid: overloaded methods are not in telescoping order.
     *
     * @see #setName(String)
     * @see #setName()
     * @see #setName(String, int)
     */
    public void getName(String value) {
    }

    // violation 5 lines below '@see tag '#field' should be placed before 'OtherClass'.'
    /**
     * Invalid: local member appears after simple class reference.
     *
     * @see OtherClass
     * @see #field
     */
    private void localAfterSimpleClass() {
    }

    // violation 6 lines below """@see tag 'OtherClass' should be placed before
    // 'OtherClass#field'."""
    /**
     * Invalid: simple class member appears before simple class reference.
     *
     * @see OtherClass#field
     * @see OtherClass
     */
    private void memberBeforeClass() {
    }

    // violation 6 lines below """@see tag 'OtherClass' should be placed before
    // 'java.util.List'."""
    /**
     * Invalid: qualified class reference appears before simple class reference.
     *
     * @see java.util.List
     * @see OtherClass
     */
    private void qualifiedBeforeSimpleClass() {
    }

    // violation 6 lines below """@see tag 'java.util.List' should be placed before
    // 'java.util'."""
    /**
     * Invalid: package reference appears before qualified class reference.
     *
     * @see java.util
     * @see java.util.List
     */
    private void packageBeforeQualifiedClass() {
    }

    // violation 6 lines below """@see tag 'java.util.List#add(Object)' should be placed
    // before 'java.util.List#add(int, Object)'."""
    /**
     * Invalid: overloaded qualified class methods are not in telescoping order.
     *
     * @see java.util.List#add(int, Object)
     * @see java.util.List#add(Object)
     */
    private void qualifiedMemberNotTelescoping() {
    }

    static class OtherClass {
        private int field;

        OtherClass() {
        }

        private void getName() {
        }

        private void setName(String value) {
        }
    }
}
