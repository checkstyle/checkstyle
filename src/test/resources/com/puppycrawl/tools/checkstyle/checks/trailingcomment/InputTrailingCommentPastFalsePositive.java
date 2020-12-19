package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

/**
 * Config:
 * default
 */
public class InputTrailingCommentPastFalsePositive {
    public static boolean mybool = true; // violation, trailing comment
    /* FROM NOW ON EVERYTHING IN THIS INPUT FILE SHOULD TRIGGER NO TRAILING COMMENT VIOLATION!!! */
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


    /* This is just a test!!!:                                                 //indent:0 exp:0
     * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
     *                                                                            //indent:1 exp:1
     * arrayInitIndent = 4                                                        //indent:1 exp:1
     * basicOffset = 4                                                            //indent:1 exp:1
     * braceAdjustment = 0                                                        //indent:1 exp:1
     * caseIndent = 4                                                             //indent:1 exp:1
     * forceStrictCondition = false                                               //indent:1 exp:1
     * lineWrappingIndentation = 4                                                //indent:1 exp:1
     * tabWidth = 4                                                               //indent:1 exp:1
     * throwsIndent = 4                                                           //indent:1 exp:1
     *                                                                            //indent:1 exp:1
     *                                                                             //indent:1 exp:1
     */

    /*
          Some text here (abcde)*/private long ID3 = 1;

    /********************* hashCode ************************/

    private static final int SHORT = 3, WORD = 4;
    private static final int CFH_LEN =
         /* version made by                 */ SHORT
         /* version needed to extract       */ + SHORT
         /* general purpose bit flag        */ + SHORT
         /* compression method              */ + SHORT
         /* last mod file time              */ + SHORT
         /* last mod file date              */ + SHORT
         /* crc-32                          */ + WORD/* compressed size                 */ + WORD
         /* uncompressed size               */ + WORD
         /* filename length                 */ + SHORT
         /* extra field length              */ + SHORT
         /* file comment length             */ + SHORT
         /* disk number start               */ + SHORT
         /* internal file attributes        */ + SHORT
         /* external file attributes        */ + WORD
         /* relative offset of local header */ + WORD;

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
