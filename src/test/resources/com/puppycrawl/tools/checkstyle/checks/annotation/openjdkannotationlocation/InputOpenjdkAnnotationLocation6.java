/*
OpenjdkAnnotationLocation
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         RECORD_DEF, COMPACT_CTOR_DEF, MODULE_DEF

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

@InputOpenjdkAnnotationLocation6 interface SingleLineInterface { }

@InputOpenjdkAnnotationLocation6 interface MultilineInterface {
// violation above 'Annotations must be on a separate line from 'MultilineInterface'.'
}

@InputOpenjdkAnnotationLocation6 enum SingleLineEnum { VALUE }

@InputOpenjdkAnnotationLocation6 enum MultilineEnum {
// violation above 'Annotations must be on a separate line from 'MultilineEnum'.'
    VALUE
}

enum EnumConstants {
    @InputOpenjdkAnnotationLocation6 VALUE,
    @InputOpenjdkAnnotationLocation6 VALUE_WITH_BODY {
    // violation above 'Annotations must be on a separate line from 'VALUE_WITH_BODY'.'
    };
}

@InputOpenjdkAnnotationLocation6 record SingleLineRecord(int value) { }

@InputOpenjdkAnnotationLocation6 record MultilineRecord(int value) {
// violation above 'Annotations must be on a separate line from 'MultilineRecord'.'
}

record CompactConstructors(int value) {
    @InputOpenjdkAnnotationLocation6 CompactConstructors { }
}

record MultilineCompactConstructor(int value) {
    @InputOpenjdkAnnotationLocation6 MultilineCompactConstructor {
    // violation above 'Annotations must be on a separate line from 'MultilineCompactConstructor'.'
    }
}
