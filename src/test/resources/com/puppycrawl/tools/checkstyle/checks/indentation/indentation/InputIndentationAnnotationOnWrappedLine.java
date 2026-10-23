/* Config:                                                                   //indent:0 exp:0
 * basicOffset = 2                                                           //indent:1 exp:1
 * braceAdjustment = 2                                                       //indent:1 exp:1
 * caseIndent = 2                                                            //indent:1 exp:1
 * lineWrappingIndentation = 4                                               //indent:1 exp:1
 * throwsIndent = 4                                                          //indent:1 exp:1
 * tabWidth = 4                                                              //indent:1 exp:1
 * arrayInitIndent = 2                                                       //indent:1 exp:1
 */                                                                          //indent:1 exp:1
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;      //indent:0 exp:0

@MyAnnotation                                                                //indent:0 exp:0
public @Ann                                                                  //indent:0 exp:0
@Ann2 class InputIndentationAnnotationOnWrappedLine                          //indent:0 exp:4 warn
    implements TestInterface {                                               //indent:4 exp:4
}                                                                            //indent:0 exp:0

final @Ann                                                                   //indent:0 exp:0
@Ann2 class ControlCase                                                      //indent:0 exp:4 warn
    implements TestInterface {                                               //indent:4 exp:4
}                                                                            //indent:0 exp:0

@MyAnnotation                                                                //indent:0 exp:0
final @Ann                                                                   //indent:0 exp:0
@Ann2(                                                                       //indent:0 exp:4 warn
    value = "x"                                                              //indent:4 exp:4
) class WrappedAnnotationCase                                                //indent:0 exp:0
    implements TestInterface {                                               //indent:4 exp:4
}                                                                            //indent:0 exp:0

@FirstAnnotation                                                             //indent:0 exp:0
@SecondAnnotation(                                                           //indent:0 exp:0
    value = "x"                                                              //indent:4 exp:4
)                                                                            //indent:0 exp:0
class LeadingAnnotationsCase {                                               //indent:0 exp:0
}                                                                            //indent:0 exp:0

@MyAnnotation                                                                //indent:0 exp:0
abstract @Ann                                                                //indent:0 exp:0
@Ann2 interface InterfaceCase                                                //indent:0 exp:4 warn
    extends TestInterface {                                                  //indent:4 exp:4
}                                                                            //indent:0 exp:0

class MemberCase {                                                           //indent:0 exp:0
  @MyAnnotation                                                              //indent:2 exp:2
  public @Ann                                                                //indent:2 exp:2
  @Ann2 String field;                                                        //indent:2 exp:6 warn

  @MyAnnotation                                                              //indent:2 exp:2
  public @Ann                                                                //indent:2 exp:2
      @Ann2 String correctField;                                             //indent:6 exp:6

  @MyAnnotation                                                              //indent:2 exp:2
  public @Ann                                                                //indent:2 exp:2
  @Ann2 void method() {                                                      //indent:2 exp:6 warn
  }                                                                          //indent:2 exp:2
}                                                                            //indent:0 exp:0

@interface Ann2 { String value() default ""; }                               //indent:0 exp:0
@interface FirstAnnotation {}                                                //indent:0 exp:0
@interface SecondAnnotation { String value() default ""; }                   //indent:0 exp:0
interface TestInterface {}                                                   //indent:0 exp:0
