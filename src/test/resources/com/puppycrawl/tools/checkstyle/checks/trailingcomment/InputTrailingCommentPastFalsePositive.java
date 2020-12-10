package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

/**
 * Config:
 * default
 */
public class InputTrailingCommentPastFalsePositive {
    public static boolean mybool = true; // violation
    public static void main(String[] args) {
        if (mybool) { /* Multiline comment with
                        embedded'       'tab
                 (not end line -> ok) */ char myChar = ' ';
        }
    }


    /** should.be.ok **/
    private boolean b = false;

    /** should 'also' be 'ok' **/
    private String s1;

    /* comment here is ok */ void foo1() {
        this.s1 = "abc";
        if (s1 == null) {

        }
        /*** THIS SHOULD BE OK ***/
        else {
            b = true;
        }
        /***/
    }

    /*this is also ok*/ void foo2() {
        this.b = true;
        /*ok*/ String s3 = "\u03bcs";
        if (b){/*okok*/}
        try {
            throw new NullPointerException();
        } catch (NullPointerException e)
        /*okok*/ {
        }
    }

    void foo3 /**ok*/ () {}

    public/*15*/void/*16*/someVeryVeryLongFunctionNameWithLotsOfComments()/*49*/{/*
    21
    */}

    // Some one line comments above
    // Then one multiline comment below

    /* Should be ok */
    private String s2 = "abc";

    /**
     * javadoc comment
     * @param a a number
     * @param b another number
     * @param c another number
     * @return a number :)
     */
    /* Block comment right after javadoc */int foo4(int a, int b, int c) {
        if (a == b) {
            s2 = c + "1";
        }
        return 1;
    }


    /* This is just a test!!!:                                                    // test
     * Test                                                                       // test
     *                                                                            // test
     * var1 = 4                                                                   // test
     * var2 = 4                                                                   // test
     * var3 = 0                                                                   // test
     * var4 = 4                                                                   // test
     * var5 = false                                                               //test
     * var6 = 4                                                                   //test
     * var7 = 4                                                                   //test
     * var8 = 4                                                                   //test
     *                                                                            //test
     *                                                                            //test
     */

    /*
          Some text here (abcde)*/private long ID3 = 1;

    /********************* testLotsOfAsterisk ************************/

    private static final int TEMP1 = 3, TEMP2 = 4;
    private static final int TEMP3 =
         /* abc                             */ TEMP1
         /* def                             */ + TEMP1
         /* 123                             */ + TEMP1
         /* 456                             */ + TEMP1
         /* 789                             */ + TEMP1
         /* 123                             */ + TEMP1
         /* 345                             */ + TEMP2/* some text                 */ + TEMP2
         /* xyz                             */ + TEMP2
         /* abcde                           */ + TEMP1;

     /* package */ static final String SPLIT_PATTERN = "split";
     /* package */ static final String JOIN_STRING = "join";
     /* package */ static final String ARRAY_BEGIN_STRING = "array-begin";
     /* package */ static final String ARRAY_END_STRING = "array-end";

     /* package */ static final String SOME_STRING = "some string";

     private /*static*/ int foo5(){
        new String() /*
                      * { { // the code below
                      * requires Eclipse 3.5
                      * setShellStyle
                      * (getShellStyle() |
                      * SWT.SHEET); } }
                      */;
        int a = 1;
        String s = "abc";
        s/*somecomment*/.toString();
        s/*
          * more comment
          */.toString();
        return 1;
     }

}
