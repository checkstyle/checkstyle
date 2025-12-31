package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;        //indent:0 exp:0

/**                                                                            //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:    //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 * arrayInitIndent = 4                                                         //indent:1 exp:1
 * basicOffset = 4                                                             //indent:1 exp:1
 * braceAdjustment = 0                                                         //indent:1 exp:1
 * caseIndent = 4                                                              //indent:1 exp:1
 * forceStrictCondition = false                                                //indent:1 exp:1
 * lineWrappingIndentation = 4                                                 //indent:1 exp:1
 * tabWidth = 4                                                                //indent:1 exp:1
 * throwsIndent = 4                                                            //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 */                                                                            //indent:1 exp:1
public class InputIndentationValidAssignIndent                                 //indent:0 exp:0
{                                                                              //indent:0 exp:0
    void foo(String[] args)                                                    //indent:4 exp:4
    {                                                                          //indent:4 exp:4
        int i = 1 +                                                            //indent:8 exp:8
            2 +                                                                //indent:12 exp:>=12
            3;                                                                 //indent:12 exp:>=12
        String line = mIndentCheck[                                            //indent:8 exp:8
            getLineNo()];                                                      //indent:12 exp:>=12
        String line1 =                                                         //indent:8 exp:8
            getLine();                                                         //indent:12 exp:>=12
        line1 =                                                                //indent:8 exp:8
            getLine();                                                         //indent:12 exp:>=12
        int i1                                                                 //indent:8 exp:8
            =                                                                  //indent:12 exp:>=12
            1;                                                                 //indent:12 exp:>=12
        i = 3;                                                                 //indent:8 exp:8

        Integer brace =                                                        //indent:8 exp:8
            (candidate == SLIST)                                               //indent:12 exp:>=12
            ? candidate : null;                                                //indent:12 exp:>=12

        AnInterfaceFooWithALongName f =                                        //indent:8 exp:8
            new AnInterfaceFooWithALongName() {                                //indent:12 exp:>=12
                public void bar() {                                            //indent:16 exp:16
                }                                                              //indent:16 exp:16
            };                                                                 //indent:12 exp:12

        AnInterfaceFooWithALongName f1                                         //indent:8 exp:8
            = new AnInterfaceFooWithALongName() {                              //indent:12 exp:>=12
                public void bar() {                                            //indent:16 exp:16
                }                                                              //indent:16 exp:16
            };                                                                 //indent:12 exp:12
// XXXX: need to be fixed                                                      //indent:0 exp:0
//         function.lastArgument().candidate = parameters;                     //indent:0 exp:0
//         function.lastArgument().candidate                                   //indent:0 exp:0
//             =                                                               //indent:0 exp:0
//             parameters;                                                     //indent:0 exp:0
        //     : add more testing                                              //indent:8 exp:8
        getMyArray()[1] = "some_value";                                        //indent:8 exp:8
        mIndentCheck[getLineNo()] = "value";                                   //indent:8 exp:8
    }                                                                          //indent:4 exp:4

    private interface AnInterfaceFooWithALongName {                            //indent:4 exp:4
        void bar();                                                            //indent:8 exp:8
    }                                                                          //indent:4 exp:4

    private static final int SLIST = 1;                                        //indent:4 exp:4
    private static final int parameters = 1;                                   //indent:4 exp:4
    int candidate = 0;                                                         //indent:4 exp:4
    private String[] mIndentCheck = null;                                      //indent:4 exp:4
    private InputIndentationValidAssignIndent function = null;                 //indent:4 exp:4
    int getLineNo() {                                                          //indent:4 exp:4
        return 1;                                                              //indent:8 exp:8
    }                                                                          //indent:4 exp:4
    String getLine() {                                                         //indent:4 exp:4
        return "";                                                             //indent:8 exp:8
    }                                                                          //indent:4 exp:4
    String[] getMyArray() {                                                    //indent:4 exp:4
        return mIndentCheck;                                                   //indent:8 exp:8
    }                                                                          //indent:4 exp:4
    InputIndentationValidAssignIndent lastArgument() {                         //indent:4 exp:4
        return this;                                                           //indent:8 exp:8
    }                                                                          //indent:4 exp:4
}                                                                              //indent:0 exp:0
