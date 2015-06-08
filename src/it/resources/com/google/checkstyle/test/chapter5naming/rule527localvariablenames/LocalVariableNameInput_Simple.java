package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

final class InputSimple
{
    public static final int badConstant = 2;
    
    public static final int MAX_ROWS = 2;

    private static int badStatic = 2;
    
    private static int sNumCreated = 0;

    private int badMember = 2;
    
    private int mNumCreated1 = 0;
     
    protected int mNumCreated2 = 0;

    private int[] mInts = new int[] {1,2, 3,
                                     4};

    /** test local variables */
    private void localVariables()
    {
        //bad examples
        int a; //warn
        int aA; //warn
        int a1_a; //warn
        int A_A; //warn
        int aa2_a; //warn
        int _a; //warn
        int _aa; //warn
        int aa_; //warn
        int aaa$aaa; //warn
        int $aaaaaa; //warn
        int aaaaaa$; //warn
        
        //good examples
        int aa;
        int aaAa1a;
        int aaAaaAa2a1;
    }
}
