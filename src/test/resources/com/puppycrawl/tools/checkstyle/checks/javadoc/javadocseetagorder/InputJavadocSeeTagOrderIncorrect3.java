/*
JavadocSeeTagOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocseetagorder;

public class InputJavadocSeeTagOrderIncorrect3 {

    // violation 7 lines below """@see tag 'java.util.ArrayList#ArrayList()' should be
    // placed before 'java.util.ArrayList#size()'."""
    /**
     * Invalid: a fully-qualified member method appears before the
     * fully-qualified member constructor of the same type.
     *
     * @see java.util.ArrayList#size()
     * @see java.util.ArrayList#ArrayList()
     */
    private void qualifiedMemberMethodBeforeConstructor() {
    }
}
