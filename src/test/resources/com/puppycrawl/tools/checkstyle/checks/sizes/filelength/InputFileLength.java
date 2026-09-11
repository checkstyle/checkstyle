/* // violation 'File length is 38 lines (max allowed is 20)'
FileLength
max = 20
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.filelength;

final class InputFileLength {
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

    private void localVariables() {
        int abc = 0;
        int ABC = 0;
        final int cde = 0;
        final int CDE = 0;
        for (int k = 0; k < 1; k++) {
            String innerBlockVariable = "";
        }
        for (int I = 0; I < 1; I++) {
            String InnerBlockVariable = "";
        }
    }
}
