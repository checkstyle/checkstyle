/* // violation
TodoComment
format = FIXME:


*/

package com.puppycrawl.tools.checkstyle.checks.todocomment;

/**
 * Contains simple mistakes:
 * - Long lines
 * - Tabs
 * - Format of variables and parameters
 * - Order of modifiers
 * @author Oliver Burn
 **/
final class InputTodoCommentSimpleOne
{
    // Long line ----------------------------------------------------------------
    // Contains a tab ->	<-
    // Contains trailing whitespace ->
    // Name format tests
    //
    /** Invalid format **/
    public static final int badConstant = 2;
    /** Valid format **/
    public static final int MAX_ROWS = 2;
    /** Invalid format **/
    private static int badStatic = 2;
    /** Valid format **/
    private static int sNumCreated = 0;
    /** Invalid format **/
    private int badMember = 2;
    /** Valid format **/
    private int mNumCreated1 = 0;
    /** Valid format **/
    protected int mNumCreated2 = 0;
    /** commas are wrong **/
    private int[] mInts = new int[] {1,2, 3,
                                     4};
    //
    // Accessor tests
    //
    /** should be private **/
    public static int sTest1;
    /** should be private **/
    protected static int sTest3;
    /** should be private **/
    static int sTest2;

    /** should be private **/
    int mTest1;
    /** should be private **/
    public int mTest2;

    //
    // Parameter name format tests
    //
    /**
     * @return hack
     * @param badFormat1 bad format
     * @param badFormat2 bad format
     * @param badFormat3 bad format
     * @throws java.lang.Exception abc
     **/
    int test1(int badFormat1,int badFormat2,
              final int badFormat3)
        throws java.lang.Exception
    {
        return 0;
    }
    /** method that is 20 lines long **/
    private void longMethod()
    {
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
    }
    /** constructor that is 10 lines long **/
    private InputTodoCommentSimpleOne()
    {
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
    }
}
/** Test enum for member naming check */
enum MyEnum1
{
    /** ABC constant */
    ABC,
    /** XYZ constant */
    XYZ;
    /** Should be mSomeMember */
    private int someMember;
    public final int blah = 5;
}
