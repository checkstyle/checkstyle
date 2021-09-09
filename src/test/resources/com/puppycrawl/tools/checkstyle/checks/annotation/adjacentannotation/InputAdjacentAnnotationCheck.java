/*
AdjacentAnnotation
tokens = CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, PARAMETER_DEF, ANNOTATION_DEF, TYPECAST, LITERAL_THROWS, \
         IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, LITERAL_NEW, DOT, ANNOTATION_FIELD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.adjacentannotation;

@interface Foo {

    String value() default "";

}

@interface Bar {}

@Foo // ok
@Bar // violation

public class InputAdjacentAnnotationCheck {

    @Foo // ok
    @Bar // violation
    /** my javadoc */
    String field1;

    @Foo // ok
    @Bar // violation

    /** my javadoc */
    String field2;

    @Foo // ok
    @Bar // violation

    public InputAdjacentAnnotationCheck(){}

    @Foo // violation

    @Bar // violation

    void m1() {}

    @Foo( value =
                  "1234" ) // ok
    @Bar // ok
    void m2() {}

    void m3( @Foo String bar, //ok
             @Bar //ok
                     String foo,
             @Foo //violation

                     String abc ) {}
}
