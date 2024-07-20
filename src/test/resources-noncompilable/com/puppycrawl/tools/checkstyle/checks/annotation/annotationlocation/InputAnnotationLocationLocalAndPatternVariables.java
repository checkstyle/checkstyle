/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

public class InputAnnotationLocationLocalAndPatternVariables {

    void test(Object obj) {
       @SuppressWarnings("deprecation") int x = 5;
       @SuppressWarnings("deprecation") int _ = 5;
       if (obj instanceof ColoredPoint( @Deprecated int y,_,_)) {}
       if (obj instanceof ColoredPoint( @Deprecated int _,_,_)) {}

       switch (obj) {
           case ColoredPoint( @Deprecated int y,_,_) -> {}
           default -> {}
       }
    }
    record ColoredPoint(int p, int x, int c) { }
}
