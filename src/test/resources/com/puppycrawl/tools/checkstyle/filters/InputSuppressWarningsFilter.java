////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.filters;

/**
 * Test input for using comments to suppress errors.
 *
 * @author Trevor Robinson
 **/
@SuppressWarnings("foo") // coverage: no following AST
class InputSuppressWarningsFilter
{
    // AST coverage
    @SuppressWarnings("foo") interface I { }
    @SuppressWarnings("foo") enum E { }
    @SuppressWarnings("foo") InputSuppressWarningsFilter() { }
    @SuppressWarnings("foo") @interface A { }

    // include a non-checkstyle suppression; suppression on same line
    @SuppressWarnings("unused") private int I; // should fail MemberNameCheck
    @SuppressWarnings({"membername"})
    private int J; // should NOT fail MemberNameCheck
    private int K; // should fail MemberNameCheck

    // DO NOT REFORMAT: L and X should be on the same line
    @SuppressWarnings(value="membername")
    private int L; private int X; // L should NOT fail, X should

    // test "checkstyle:" prefix
    @SuppressWarnings("checkstyle:ConstantName")
    private static final int m = 0; // should NOT fail ConstantNameCheck
    private static final int n = 0; // should fail ConstantNameCheck

    // test explicit warning alias
    @SuppressWarnings("paramnum")
    // should NOT fail ParameterNumberCheck
    public void needsLotsOfParameters(@SuppressWarnings("unused") int a,
        int b, int c, int d, int e, int f, int g, int h)
    {
        @SuppressWarnings("unused") int z;
        try {
        }
        catch (Exception ex) {
            // should fail IllegalCatchCheck
        }
    }

    // test fully qualified annotation name
    @java.lang.SuppressWarnings("illegalCatch")
    public void needsToCatchException()
    {
        try {
        }
        catch (Exception ex) {
            // should NOT fail IllegalCatchCheck
        }
    }

    enum AnEnum {
        @SuppressWarnings("rawtypes")
        ELEMENT;
    }
    private static final String UNUSED="UnusedDeclaration";

    @SuppressWarnings(UNUSED)
    public void annotationUsingStringConstantValue(){
    }
}
