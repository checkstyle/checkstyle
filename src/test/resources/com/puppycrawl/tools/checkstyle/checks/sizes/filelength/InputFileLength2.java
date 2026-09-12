/*
FileLength
max = 38
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.filelength;

final class InputFileLength2 {
    private static int badStatic = 2;
    private int badMember = 2;
    protected int mNumCreated = 0;

    public static int sTest1;
    protected static int sTest3;
    int mTest1;
    public int mTest2;

    int test1(int badFormat1, int badFormat2, final int badFormat3)
            throws Exception {
        return badFormat1 + badFormat2 + badFormat3;
    }
    // Keep this fixture at the configured maximum file length.
    // These lines ensure the boundary condition is exercised.
    // The file should pass when its length equals the configured maximum.
    // A changed conditional boundary must be detected by this test.
    // The remaining lines intentionally keep the fixture at the boundary.
    // This avoids relying on unrelated oversized test input.
    // The fixture remains below the repository-wide resource limit.
    // The exact line count is part of the test's purpose.
    // Do not remove these lines without updating the configured maximum.
    // This documents why the otherwise empty comments are present.
    // The boundary test must continue to distinguish greater-than behavior.
    // The check should not report a diagnostic at exactly the maximum.
    // End of boundary-condition fixture padding.
}
