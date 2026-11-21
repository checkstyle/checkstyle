package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;       //indent:0 exp:0

/* Config:                                                                    //indent:0 exp:0
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 */                                                                           //indent:1 exp:1

public class InputIndentationAnnotationArrayClosingParenthesis {              //indent:0 exp:0

    @APIResponses({                                                           //indent:4 exp:4
        @APIResponse(responseCode = "200", description = "Success"),          //indent:8 exp:8
        @APIResponse(                                                         //indent:8 exp:8
            responseCode = "404",                                             //indent:12 exp:12
            description = "Not found"),                                       //indent:12 exp:12
        @APIResponse(                                                         //indent:8 exp:8
            responseCode = "500",                                             //indent:12 exp:12
            description = "Internal error"                                    //indent:12 exp:12
        )                                                                     //indent:8 exp:8
    })                                                                        //indent:4 exp:4
    public void get() {                                                       //indent:4 exp:4
        final int[] bar = new int[] {                                         //indent:8 exp:8
            1,                                                                //indent:12 exp:12
            2,                                                                //indent:12 exp:12
            3                                                                 //indent:12 exp:12
        };                                                                    //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    private @interface APIResponse {                                          //indent:4 exp:4
        String responseCode();                                                //indent:8 exp:8

        String description();                                                 //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    private @interface APIResponses {                                         //indent:4 exp:4
        APIResponse[] value() default {};                                     //indent:8 exp:8
    }                                                                         //indent:4 exp:4
}                                                                             //indent:0 exp:0
