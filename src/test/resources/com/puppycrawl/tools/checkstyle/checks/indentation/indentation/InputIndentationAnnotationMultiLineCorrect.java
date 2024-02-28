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
@X//indent:0 exp:0
public @Z//indent:0 exp:0
    @W class InputIndentationAnnotationMultiLineCorrect//indent:4 exp:4
    implements MyInterfaceTwo {//indent:4 exp:4
//indent:0 exp:0
//indent:0 exp:0
}//indent:0 exp:0
@M class AnotherClassTwo {}//indent:0 exp:0
//indent:0 exp:0
@interface X {}//indent:0 exp:0
@interface Y {}//indent:0 exp:0
@interface Z {}//indent:0 exp:0
@interface W {}//indent:0 exp:0
@interface M {}//indent:0 exp:0
@interface K {}//indent:0 exp:0
@interface P {}//indent:0 exp:0
@interface Q {}//indent:0 exp:0
interface MyInterfaceTwo {}//indent:0 exp:0
//indent:0 exp:0
@X @Y//indent:0 exp:0
final @P @K//indent:0 exp:0
    @M class MyClassTwoTwo//indent:4 exp:4
    implements MyInterfaceTwo {}//indent:4 exp:4
//indent:0 exp:0
@X @Y//indent:0 exp:0
abstract @P//indent:0 exp:0
    @M @K class HisClassTwo//indent:4 exp:4
    implements MyInterfaceTwo {}//indent:4 exp:4