/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, STATIC_INIT, INSTANCE_INIT, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace
.emptylineseparator; // some comment

import java.util.function.Supplier;

public class InputEmptyLineSeparatorMultipleLines {
    void a() {
    }


    /** Javadoc. */
    @Deprecated // violation ''METHOD_DEF' has more than 1 empty lines before.'
    void b() {
    }


    void x() {} // violation ''METHOD_DEF' has more than 1 empty lines before.'
    @Deprecated // violation ''METHOD_DEF' should be separated from previous line.'
    void e() {
    }


    /* line one
     * line two
     * line three */
    @Deprecated // violation ''METHOD_DEF' has more than 1 empty lines before.'
    void f() {
    }

    void c() {
    }

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/


    @SuppressWarnings({"unchecked"}) // violation ''METHOD_DEF' has more than 1 empty lines before.'
    void d() {
    }

    @Test
    void test_FOO_bar() {
    }


    @Test // violation ''METHOD_DEF' has more than 1 empty lines before.'
    void testing_a() {
    }

    @MyAnnotation6
    @MyAnnotation5
    public int field; // ok, no blank lines here

    @Override
    public boolean nodeHasJoinedClusterOnce() {
        return true;
    }

    /** end of {@link Supplier} implementation */


    @Override
    public void publish() { // violation above ''METHOD_DEF' has more than 1 empty lines before.'
    }




    @WrappedLinesAnnotation1 // violation ''CLASS_DEF' has more than 1 empty lines before.'
    @WrappedLinesAnnotation3(
        "value"
    )
    public static class Nested {
    }

    void y() {
    }


    public void z() { // violation ''METHOD_DEF' has more than 1 empty lines before.'
    }

    @interface WrappedLinesAnnotation1 {}

    @interface WrappedLinesAnnotation3 {

        String value();
    }

    @interface MyAnnotation6{};

    @interface MyAnnotation5{};

    @interface Override{}

    @interface Test{}
}
