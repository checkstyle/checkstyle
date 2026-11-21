/*
JavadocSeeTagOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocseetagorder;

public class InputJavadocSeeTagOrderTrailingDot {

    /**
     * Valid: a type reference with a trailing dot cannot be classified,
     * so it is ignored for ordering purposes.
     *
     * @see java.util.List.
     */
    private void trailingDotType() {
    }

    /**
     * Valid: a package-like reference with a trailing dot is ignored too.
     *
     * @see java.util.List
     * @see java.util.
     */
    private void trailingDotPackage() {
    }
}
