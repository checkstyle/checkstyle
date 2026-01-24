/* Config:                                                                     //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 * basicOffset = 4                                                             //indent:1 exp:1
 * braceAdjustment = 0                                                         //indent:1 exp:1
 * caseIndent = 4                                                              //indent:1 exp:1
 * forceStrictCondition = true                                                 //indent:1 exp:1
 * lineWrappingIndentation = 4                                                 //indent:1 exp:1
 * tabWidth = 4                                                                //indent:1 exp:1
 * throwsIndent = 4                                                            //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 */                                                                            //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;        //indent:0 exp:0

/**                                                                            //indent:0 exp:0
 * Test for Indentation check regression with "new".                           //indent:1 exp:1
 */                                                                            //indent:1 exp:1
public class InputIndentationNewWithTernaryOp {                                //indent:0 exp:0

    public Integer foo(boolean flag) {                                         //indent:4 exp:4
        Integer result = flag ?                                                //indent:8 exp:8
            new Integer(1) :                                                   //indent:12 exp:12
            new Integer(2);                                                    //indent:12 exp:12

        return result;                                                         //indent:8 exp:8
    }                                                                          //indent:4 exp:4

    public Integer bar(boolean flag) {                                         //indent:4 exp:4
        return flag ?                                                          //indent:8 exp:8
            new Integer(1) : new Integer(2);                                   //indent:12 exp:12
    }                                                                          //indent:4 exp:4

    // new only in "then" branch (after ?)                                     //indent:4 exp:4
    public String baz(boolean flag) {                                          //indent:4 exp:4
        String result = flag ?                                                 //indent:8 exp:8
            new StringBuilder("hello").toString() :                            //indent:12 exp:12
            "world";                                                           //indent:12 exp:12
        return result;                                                         //indent:8 exp:8
    }                                                                          //indent:4 exp:4

    // new only in "else" branch (after :)                                     //indent:4 exp:4
    public String qux(boolean flag) {                                          //indent:4 exp:4
        String result = flag ?                                                 //indent:8 exp:8
            "hello" :                                                          //indent:12 exp:12
            new StringBuilder("world").toString();                             //indent:12 exp:12
        return result;                                                         //indent:8 exp:8
    }                                                                          //indent:4 exp:4

}                                                                              //indent:0 exp:0
