/* Config: */                                                         //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

//below indent:0 exp:0
@InputIndentationAnnotationArrayInitComments.Values({
   1                                                                  //indent:3 exp:4 warn
})                                                                    //indent:0 exp:0
public class InputIndentationAnnotationArrayInitComments {            //indent:0 exp:0
    @Values({ // comment                                              //indent:4 exp:4
       1                                                              //indent:7 exp:8 warn
    })                                                                //indent:4 exp:4
    int line;                                                         //indent:4 exp:4
    @Values({ /* comment */                                           //indent:4 exp:4
       1                                                              //indent:7 exp:8 warn
    })                                                                //indent:4 exp:4
    int block;                                                        //indent:4 exp:4
    @Values({ // comment                                              //indent:4 exp:4
        1                                                             //indent:8 exp:8
    })                                                                //indent:4 exp:4
    int valid;                                                        //indent:4 exp:4
    @interface Values {                                               //indent:4 exp:4
        int[] value();                                                //indent:8 exp:8
    }                                                                 //indent:4 exp:4
}                                                                     //indent:0 exp:0
