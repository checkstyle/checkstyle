/*
JavadocSeeTagOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocseetagorder;

/**
 * Unparseable @see tags are ignored for ordering.
 * Exercises SeeReference.from() returning null for non-REFERENCE tags.
 *
 * @see "free text"
 * @see <a href="https://example.com">link</a>
 * @see #field
 */
public class InputJavadocSeeTagOrderEdgeCases {

    private String field;

    /**
     * Simple lowercase name that is not a type reference.
     * Exercises the false branch of isPackageReference when no dot is present.
     *
     * @see somelowername
     * @see #field
     */
    private void simpleLowercase() {
    }

    /**
     * Dotted name with mixed-case last segment that is not a package.
     * Exercises the false branch of isPackageReference when dot is present.
     *
     * @see com.example.myClass
     * @see #field
     */
    private void dottedNonPackage() {
    }

    /**
     * Dotted name with a mixed-case interior segment and an all-lowercase last
     * segment is not a package reference either.
     * Exercises isPackageReference checking every segment, not only the last one.
     *
     * @see com.Example.util
     * @see #field
     */
    private void dottedNonPackageWithUppercaseInteriorSegment() {
    }

    /**
     * Field and callable with same name: field before callable is valid.
     * Exercises the false branch of isField comparison in isGroupingViolation.
     *
     * @see #getValue
     * @see #getValue()
     */
    private void fieldAndCallableSameName() {
    }

    private int getValue;

    private void getValue() {
    }

    /**
     * Different classes' members that share a simple name are independent
     * overload groups; an unrelated reference between them must not be
     * treated as breaking the grouping of either class's overloads.
     * Exercises the qualifier-scoped grouping/telescoping key.
     *
     * @see OtherClass#getValue()
     * @see OtherClass#other()
     * @see AnotherClass#getValue()
     */
    private void differentClassesSameMemberName() {
    }

    /**
     * A field interloper with a different name does not make a later
     * same-name callable look wrongly grouped, because the field/callable
     * kinds differ between the interloper and the callable.
     * Exercises the previousReference.isField() comparison in
     * isGroupingViolation.
     *
     * @see #baz
     * @see #foo
     * @see #baz()
     */
    private void fieldInterloperDifferentKind() {
    }

    private String baz;

    private String foo;

    private void baz() {
    }

    static class OtherClass {
        private void getValue() {
        }

        private void other() {
        }
    }

    static class AnotherClass {
        private void getValue() {
        }
    }
}
