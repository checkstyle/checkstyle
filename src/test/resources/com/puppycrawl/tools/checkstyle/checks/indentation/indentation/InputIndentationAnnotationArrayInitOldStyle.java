/*                                                                          //indent:0 exp:0
 * Indentation                                                              //indent:1 exp:1
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * forceStrictCondition = false                                             //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

@JsonSubTypes( //indent:0 exp:0
    { //indent:4 exp:4
        @Type(value="something") //indent:8 exp:8
    } //indent:4 exp:4
) //indent:0 exp:0

@interface JsonSubTypes { //indent:0 exp:0
    Type[] value() default {}; //indent:4 exp:4
} //indent:0 exp:0

@interface Type { String value() default "hello"; } //indent:0 exp:0

public class InputIndentationAnnotationArrayInitOldStyle {} //indent:0 exp:0
