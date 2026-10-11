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

    /**
     * Valid: an unqualified type reference and a fully-qualified type
     * reference that happen to share the same simple name belong to
     * different structural categories (simple vs. qualified), so an
     * unrelated qualified reference between them is not a grouping
     * violation.
     *
     * @see List
     * @see java.util.Map
     * @see java.awt.List
     */
    private void simpleAndQualifiedTypeShareName() {
    }

    /**
     * Valid: overloaded methods with non-zero, increasing parameter
     * counts are already in telescoping order.
     *
     * @see #resize(int)
     * @see #resize(int, int)
     */
    private void nonZeroTelescopingOrder() {
    }

    private void resize(int width) {
    }

    private void resize(int width, int height) {
    }

    /**
     * Valid: a {@code Type##fragment} reference links to a named fragment
     * within a page rather than to a member, so it is ignored for ordering
     * purposes; it does not count as the simple type "Double" appearing
     * out of order after a qualified member reference.
     *
     * @see java.lang.Double#valueOf(String)
     * @see Double##decimalToBinaryConversion Decimal Binary Conversion Issues
     */
    private void fragmentReferenceIsIgnored() {
    }

    /**
     * Valid: a bare {@code ##fragment} reference with no leading type name
     * is also ignored, rather than being treated as a local member
     * reference with no member to compare.
     *
     * @see #value
     * @see ##someFragment Some Description
     */
    private void bareFragmentReferenceIsIgnored() {
    }

    private int value;
}
