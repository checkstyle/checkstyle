package com.puppycrawl.tools.checkstyle.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
@DifficultAnnotation({ //indent:0 exp:0
    @MyType(value = Boolean.class, //indent:4 exp:4
        name = "boolean"), //indent:8 exp:>=8
    @MyType(value = String.class, name = "string") }) //indent:4 exp:4
@SimpleAnnotation //indent:0 exp:0
public class InputDifficultAnnotations { //indent:0 exp:0

    @DifficultAnnotation({ //indent:4 exp:4
        @MyType(value = Boolean.class, name = "boolean"), //indent:8 exp:8
        @MyType(value = String.class, name = "string") }) //indent:8 exp:8
    @SimpleAnnotation //indent:4 exp:4
    String foo = "foo"; //indent:4 exp:4

    @DifficultAnnotation({ //indent:4 exp:4
        @MyType(value = Boolean.class, name = "boolean"), //indent:8 exp:8
        @MyType(value = String.class, name = "string") }) //indent:8 exp:8
    @SimpleAnnotation //indent:4 exp:4
    void foo() { //indent:4 exp:4

    } //indent:4 exp:4
} //indent:0 exp:0

@DifficultAnnotation({ //indent:0 exp:0
@MyType(value = Boolean.class, name = "boolean"), //indent:0 exp:4 warn
@MyType(value = String.class, name = "string") }) //indent:0 exp:4 warn
class IncorrectClass { //indent:0 exp:0

    @DifficultAnnotation({ //indent:4 exp:4
        @MyType(value = Boolean.class, name = "boolean"), //indent:8 exp:8
        @MyType(value = String.class, name = "string") }) //indent:8 exp:8
    String foo = "foo"; //indent:4 exp:4

    @DifficultAnnotation({ //indent:4 exp:4
      @MyType(value = Boolean.class, name = "boolean"), //indent:6 exp:8 warn
        @MyType(value = String.class, name = "string") }) //indent:8 exp:8
    void foo() { //indent:4 exp:4

    } //indent:4 exp:4
} //indent:0 exp:0

@interface DifficultAnnotation { //indent:0 exp:0

    MyType[] value(); //indent:4 exp:4

} //indent:0 exp:0

@interface MyType { //indent:0 exp:0

    Class<?> value(); //indent:4 exp:4

    String name(); //indent:4 exp:4
} //indent:0 exp:0

@interface SimpleAnnotation {} //indent:0 exp:0
