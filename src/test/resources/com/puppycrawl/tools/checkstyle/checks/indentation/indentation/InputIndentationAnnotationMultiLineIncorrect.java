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
@A//indent:0 exp:0
public @G//indent:0 exp:0
@N class InputIndentationAnnotationMultiLineIncorrect//indent:0 exp:4 warn
    implements MyInterface {//indent:4 exp:4
//indent:0 exp:0
//indent:0 exp:0
}//indent:0 exp:0
@T class AnotherClass {}//indent:0 exp:0
//indent:0 exp:0
@interface A {}//indent:0 exp:0
@interface B {}//indent:0 exp:0
@interface G {}//indent:0 exp:0
@interface N {}//indent:0 exp:0
@interface T {}//indent:0 exp:0
@interface C {}//indent:0 exp:0
@interface D {}//indent:0 exp:0
@interface E {}//indent:0 exp:0
interface MyInterface {}//indent:0 exp:0
//indent:0 exp:0
@A @B//indent:0 exp:0
final @C @D//indent:0 exp:0
@E class MyClass//indent:0 exp:4 warn
    implements MyInterface {}//indent:4 exp:4
//indent:0 exp:0
@A @B//indent:0 exp:0
abstract @C//indent:0 exp:0
@E @D class HisClass//indent:0 exp:4 warn
    implements MyInterface {}//indent:4 exp:4
