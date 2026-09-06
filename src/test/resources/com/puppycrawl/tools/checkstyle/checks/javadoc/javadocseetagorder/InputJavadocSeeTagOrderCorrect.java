/*
JavadocSeeTagOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocseetagorder;

import java.util.List;

/**
 * Valid order: local field, local constructors, local methods, simple class,
 * simple class members, qualified class, qualified class members, package.
 *
 * @see #field
 * @see #Example()
 * @see #Example(String)
 * @see #getName()
 * @see #getName(String)
 * @see OtherClass
 * @see OtherClass#field
 * @see OtherClass#OtherClass()
 * @see OtherClass#OtherClass(String)
 * @see OtherClass#getName()
 * @see OtherClass#getName(String)
 * @see java.util.List
 * @see java.util.List#size()
 * @see java.util
 */
public class InputJavadocSeeTagOrderCorrect {

    private String field;

    public InputJavadocSeeTagOrderCorrect() {
    }

    public InputJavadocSeeTagOrderCorrect(String value) {
    }

    private void getName() {
    }

    private void getName(String value) {
    }

    static class OtherClass {
        private int field;

        OtherClass() {
        }

        OtherClass(String value) {
        }

        private void getName() {
        }

        private void getName(String value) {
        }
    }
}
