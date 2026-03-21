/*
SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck=paramnum

com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
id = ignore
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true

com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck
id =
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true

com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheck
id = ignore
excludedClasses = (default)^$

com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck
max = (default)7
ignoreOverriddenMethods = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF

com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck
illegalClassNames = (default)Error, Exception, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.Exception, java.lang.RuntimeException, java.lang.Throwable

com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck
scope = PRIVATE
excludeScope = (default)null
skipAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF

*/
package com.puppycrawl.tools.checkstyle.filters.suppresswarningsfilter;
/** @author Trevor Robinson */
@SuppressWarnings("foo") // coverage: no following AST
class InputSuppressWarningsFilter {
    // AST coverage
    @SuppressWarnings("foo") interface I { } // violation 'Missing a Javadoc comment'
    @SuppressWarnings("foo") enum E { } // violation 'Missing a Javadoc comment'
    @SuppressWarnings("foo") InputSuppressWarningsFilter() { }
    @SuppressWarnings("foo") @interface A { } // violation 'Missing a Javadoc comment'

    // include a non-checkstyle suppression; suppression on same line
    @SuppressWarnings("unused") int I; // violation ''I' must match .* \Q'^[a-z][a-zA-Z0-9]*$'\E'
    @SuppressWarnings({"membername"})
    private int J; // filtered violation 'Name 'J' must match pattern \Q'^[a-z][a-zA-Z0-9]*$'\E'
    private int K; // violation 'Name 'K' must match pattern \Q'^[a-z][a-zA-Z0-9]*$'\E'

    // DO NOT REFORMAT: L and X should be on the same line
    @SuppressWarnings(value="membername")
    private int L; private int X; // violation
    // filtered violation above

    // test "checkstyle:" prefix
    @SuppressWarnings("checkstyle:ConstantName")
    private static final int m = 0; // filtered violation
    private static final int n = 0; // violation

    // test explicit warning alias
    @SuppressWarnings("paramnum")
    // should NOT fail ParameterNumberCheck
    void foo(@SuppressWarnings("unused") int a, // filtered violation 'More than 7 param.* (.* 8)'
        int b, int c, int d, int e, int f, int g, int h) {
        @SuppressWarnings("unused") int z;
        try { }
        catch (Exception ex) { // violation 'Catching 'Exception' is not allowed'
            // should fail IllegalCatchCheck
        }
    }

    // test fully qualified annotation name
    @java.lang.SuppressWarnings("illegalCatch")
    public void needsToCatchException() {
        try { }
        catch (Exception ex) { // filtered violation 'Catching 'Exception' is not allowed'
            // should NOT fail IllegalCatchCheck
        }
    }

    enum AnEnum { // violation 'Missing a Javadoc comment'
        @SuppressWarnings("rawtypes")
        ELEMENT;
    }
    private static final String UNUSED="UnusedDeclaration";

    @SuppressWarnings(UNUSED)
    public void annotationUsingStringConstantValue(){ }

    @SuppressWarnings("checkstyle:uncommentedmain") // filtered violation 'Uncommented main method'
    public static void main(String[] args) { }

    static class TestClass1 { // violation 'Missing a Javadoc comment'
        @SuppressWarnings("uncommentedmain") // filtered violation 'Uncommented main method found'
        public static void main(String[] args) { }
    }

    static class TestClass2 { // violation 'Missing a Javadoc comment'
        @SuppressWarnings("UncommentedMain") // filtered violation 'Uncommented main method found'
        public static void main(String[] args) { }
    }

    static class TestClass3 { // violation 'Missing a Javadoc comment'
        @SuppressWarnings("checkstyle:UncommentedMain") // filtered violation 'Uncomm.* main method'
        public static void main(String[] args) { }
    }

    @SuppressWarnings("checkstyle:javadoctype") // violation 'Missing a Javadoc comment'
    public static abstract class Task { }
}
