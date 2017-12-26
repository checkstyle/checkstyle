package com.puppycrawl.tools.checkstyle.checks.whitespace.beforerightcurly;

/**
 * Type test cases for BeforeRightCurlyCheck.
 */

/** CLASS_DEF */
class EmptyClass {}

class NoEmptyLine{NoEmptyLine(){}}

class EmptyLine{
    EmptyLine(){
    }
    class Inner {
        class Inner2
        {
            Inner2() {}

        }
    }

}

/** INTERFACE_DEF */

interface EmptyInterface{

}

interface InterfaceNoEmptyLine
{
    int method1();
    void method2();
}

interface InterfaceEmptyLine
{
    int method1();
    void method2();

}

/** ENUM_DEF, ENUM_CONSTANT_DEF */

enum EmptyEnum {
}

enum EmptyEnumValue {
    ONE(""){};
    EmptyEnumValue(String value) {   }
}

@AnnotationNoEmptyLine({1, 2})
enum EnumNoEmptyLine
{
    ONE, TWO(""){/**/};

    EnumNoEmptyLine() { }
    EnumNoEmptyLine(String value) {   }
}

@AnnotationEmptyLine({
        1, 2

})
enum EnumEmptyLine
{
    ONE, TWO(""){}, THREE{
        //

    };

    private String value = null;

    EnumEmptyLine() { }
    EnumEmptyLine(String value) {
        this.value = value;
    }

}

@interface Annotation{
}

@interface EmptyAnnotation{}

@interface AnnotationNoEmptyLine
{
    int[] value() default { 1, 2 };
}

@interface AnnotationEmptyLine
{
    int[] value() default {
            1,
            2,

    };

}
