package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;//indent:0 exp:0
//indent:0 exp:0
/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 2                                                        //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 2                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 */                                                                           //indent:1 exp:1
//indent:0 exp:0
@AppleAnnotation//indent:0 exp:0
public @GrapeAnnotation//indent:0 exp:0
@NutsAnnotation class InputIndentationAnnotationMultiLine//indent:0 exp:4 warn
    implements MyInterface {//indent:4 exp:4
//indent:0 exp:0
//indent:0 exp:0
}//indent:0 exp:0
//indent:0 exp:0
@interface AppleAnnotation {//indent:0 exp:0
//indent:0 exp:0
}//indent:0 exp:0
//indent:0 exp:0
@interface GrapeAnnotation {//indent:0 exp:0
//indent:0 exp:0
}//indent:0 exp:0
//indent:0 exp:0
@interface NutsAnnotation {//indent:0 exp:0
//indent:0 exp:0
}//indent:0 exp:0
//indent:0 exp:0
@interface ThisAnnotation {//indent:0 exp:0
//indent:0 exp:0
}//indent:0 exp:0
interface MyInterface {//indent:0 exp:0
}//indent:0 exp:0
