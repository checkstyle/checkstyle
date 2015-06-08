package com.google.checkstyle.test.chapter5naming.rule525nonconstantfieldnames;
import java.io.*;

final class InputSimple
{

    public static final int badConstant = 2;

    public static final int MAX_ROWS = 2;

    
    private int bad$Static = 2; //warn
   
    private static int sum_Created = 0;

   
    private int bad_Member = 2; //warn

    private int m = 0; //warn

    protected int m_M = 0; //warn


    private int[] m$nts = new int[] {1,2, 3, //warn
                                     4};


    public static int sTest1;

    protected static int sTest3;

    static int sTest2;


    int mTest1; //warn

    public int mTest2; //warn
    
    public int $mTest2; //warn
    
    public int mTes$t2; //warn

    public int mTest2$; //warn
    
    /** test local variables. This Check doesn't verify local variables, only members.*/
    private void localVariables()
    {
        int a;
        int aA;
        int a1_a;
        int A_A;
        int aa2_a;
        int _a;
        int _aa;
        int aa_;
        int aaa$aaa;
        int $aaaaaa;
        int aaaaaa$;
        
        int aa;
        int aaAa1a;
        int aaAaaAa2a1;
    }
    
    interface Foo {
        public void greet();
    
    }
    
    class InnerClass
    {
        public static final int badConstant = 2;

        public static final int MAX_ROWS = 2;

        
        private int bad$Static = 2; //warn
       
        private  int sum_Created = 0; //warn

       
        private int bad_Member = 2; //warn

        private int m = 0; //warn

        protected int m_M = 0; //warn


        private int[] m$nts = new int[] {1,2, 3, //warn
                                         4};


        int mTest1; //warn

        public int mTest2; //warn
        
        public int $mTest2; //warn
        
        public int mTes$t2; //warn

        public int mTest2$; //warn
        
        void fooMethod()
        {
            Foo foo = new Foo() {
                    
                    int bad$Static = 2; //warn
                   
                    int sum_Created = 0; //warn

                   
                    int bad_Member = 2; //warn

                    int m = 0; //warn

                    int m_M = 0; //warn


                    int[] m$nts = new int[] {1,2, 3, //warn
                                                     4};


                    int mTest1; //warn

                    int mTest2; //warn
                    
                    int $mTest2; //warn
                    
                    int mTes$t2; //warn

                    int mTest2$; //warn

                    public void greet() {}
            };
        }
    }
}
