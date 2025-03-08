/*
AnnotationOnSameLine
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE_USE;

public class InputAnnotationOnSameLine {
    // violation below, 'Annotation 'Ann' should be on the same line with its target.'
    @Ann public
    @Ann2 class E {}

    // violation below, 'Annotation 'Ann' should be on the same line with its target.'
    @Ann private
    @Ann2 class A {}

    // violation below, 'Annotation 'Ann' should be on the same line with its target.'
    @Ann sealed
    @Ann2 class B permits C {}

    // violation below, 'Annotation 'Ann' should be on the same line with its target.'
    @Ann final
    @Ann2 class C extends B {}

    @Ann public @Ann2 class Egood {}

    @Ann private @Ann2 class Agood {}

    @Ann sealed @Ann2 class Bgood permits Cgood {}

    @Ann final @Ann2 class Cgood extends Bgood {}

    @Target({TYPE_USE}) @interface Ann {}
    @Target({TYPE_USE}) @interface Ann2 {}
}
