/*
JavadocSeeTagOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocseetagorder;

public class InputJavadocSeeTagOrderEdgeCases2 {

    /**
     * Valid: distinct fully-qualified types with different simple names
     * are independent of each other, even though none of them shares a
     * class with another.
     *
     * @see java.util.List
     * @see java.util.Set
     * @see java.util.Map
     */
    private void distinctQualifiedTypes() {
    }
}
