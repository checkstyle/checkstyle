/* Config:                                                                     //indent:0 exp:0
 * This test input is intended to be checked using following configuration:    //indent:1 exp:1
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
 * Test for new keyword indentation in ternary expressions.                    //indent:1 exp:1
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

    public String baz(boolean flag) {                                          //indent:4 exp:4
        String result = flag ?                                                 //indent:8 exp:8
            new StringBuilder("hello").toString() :                            //indent:12 exp:12
            "world";                                                           //indent:12 exp:12
        return result;                                                         //indent:8 exp:8
    }                                                                          //indent:4 exp:4

    public String qux(boolean flag) {                                          //indent:4 exp:4
        String result = flag ?                                                 //indent:8 exp:8
            "hello" :                                                          //indent:12 exp:12
            new StringBuilder("world").toString();                             //indent:12 exp:12
        return result;                                                         //indent:8 exp:8
    }                                                                          //indent:4 exp:4

    void testMethodCallWithTernaryNewCorrect() {                               //indent:4 exp:4
        java.util.Map<Object, Object> subGroupMap = new java.util.HashMap<>(); //indent:8 exp:8
        Object head = new Object();                                            //indent:8 exp:8
        Object[] subGroup = new Object[2];                                     //indent:8 exp:8
        for (int i = 0; i < 10; i++) {                                         //indent:8 exp:8
            subGroupMap.put(head, subGroup.length > 0 ?                        //indent:12 exp:12
                new Object() : null);                                          //indent:16 exp:16
        }                                                                      //indent:8 exp:8
    }                                                                          //indent:4 exp:4

    private Exception cached;                                                  //indent:4 exp:4
    public void testThrowNew() throws Exception {                              //indent:4 exp:4
        throw                                                                  //indent:8 exp:8
            new RuntimeException("error");                                     //indent:12 exp:12
    }                                                                          //indent:4 exp:4

    public void testThrowWithTernaryNew() throws Exception {                   //indent:4 exp:4
        throw cached != null ?                                                 //indent:8 exp:8
            cached :                                                           //indent:12 exp:12
            new RuntimeException("error");                                     //indent:12 exp:12
    }                                                                          //indent:4 exp:4

    public Integer nestedTernaryMultiLine(boolean flag1, boolean flag2) {      //indent:4 exp:4
        Integer result = flag1 ?                                               //indent:8 exp:8
            flag2 ?                                                            //indent:12 exp:12
                new Integer(1) :                                               //indent:16 exp:16
                new Integer(2) :                                               //indent:16 exp:16
            new Integer(3);                                                    //indent:12 exp:12
        return result;                                                         //indent:8 exp:8
    }                                                                          //indent:4 exp:4

    public Integer nestedTernary(boolean flag1, boolean flag2) {               //indent:4 exp:4
        Integer result = flag1 ?                                               //indent:8 exp:8
            flag2 ? new Integer(1) : new Integer(2) :                          //indent:12 exp:12
            new Integer(3);                                                    //indent:12 exp:12
        return result;                                                         //indent:8 exp:8
    }                                                                          //indent:4 exp:4

}                                                                              //indent:0 exp:0
