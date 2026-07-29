/*
OpenjdkAnnotationLocation
tokens = CLASS_DEF, ANNOTATION_DEF, CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation;

@interface InputOpenjdkAnnotationLocation6 {
    String value() default "";
}

@interface Annotation17 { }

// Taken from OpenJDK 25: AnnotationTest.java.
@InputOpenjdkAnnotationLocation6(value = "") class C { }

@InputOpenjdkAnnotationLocation6(value = "") class MultilineC {
// violation above 'Annotations must be on a separate line from 'MultilineC'.'
}

// Taken from OpenJDK 25: RecursiveAnnotation.java.
@InputOpenjdkAnnotationLocation6 @Annotation17 @interface Rat { }

@InputOpenjdkAnnotationLocation6 @Annotation17 @interface MultilineRat {
// violation above 'Annotations must be on a separate line from 'MultilineRat'.'
}

class GenericMethods1 {

    // Taken from OpenJDK 25: AnnotatedTypeVariableTest.java.
    @InputOpenjdkAnnotationLocation6 <M extends Error> GenericMethods1() throws M { }
}

class GenericMethods2 {

    @InputOpenjdkAnnotationLocation6 GenericMethods2() {
    // violation above 'Annotations must be on a separate line from 'GenericMethods2'.'
    }
}
