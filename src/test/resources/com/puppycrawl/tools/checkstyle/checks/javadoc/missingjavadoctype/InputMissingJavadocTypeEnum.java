/*
MissingJavadocType
excludeScope = (default)null
scope = PRIVATE
skipAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

/**
 * Tests Enum with private
 **/
public class InputMissingJavadocTypeEnum {

    /**
     * A specification for which index to return.
     *
     */ enum Test {
      /**
       *
       */
      Test{
            @Override
            <T> void method(
                    T value) { }
        };

        abstract <T> void method(
                T value);
    }

    // violation below 'Missing a Javadoc comment.'
     enum Test2 {
        TEST_2 {
            @Override
            <T> void method(
                    T value) { }
        };

        abstract <T> void method(
                T value);
    }

    ; /** @deprecated */ ;

    // violation below 'Missing a Javadoc comment.'
    static class A {

    }

}

