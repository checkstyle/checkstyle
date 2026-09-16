/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, STATIC_INIT, INSTANCE_INIT, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace
.emptylineseparator; import java.util.function.Supplier;
public class // violation ''CLASS_DEF' should be separated from previous line.'
InputEmptyLineSeparatorMultipleLines2 {
        void a() {
    }


    /** Javadoc. */
    @Deprecated
    void b() {
    }


    void x() {}
    @Deprecated // violation ''METHOD_DEF' should be separated from previous line.'
    void e() {
    }


    /* line one
     * line two
     * line three */
    @Deprecated
    void f() {
    }

    void c() {
    }

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/


    @SuppressWarnings({"unchecked"})
    void d() {
    }

    @Test
    void test_FOO_bar() {
    }


    @Test
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
    public void publish() {
    }




    @WrappedLinesAnnotation1
    @WrappedLinesAnnotation3(
        "value"
    )
    public static class Nested {
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
