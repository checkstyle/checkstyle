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

     enum Test2 { // violation 'Missing a Javadoc comment.'
        TEST_2 {
            @Override
            <T> void method(
                    T value) { }
        };

        abstract <T> void method(
                T value);
    }

    ; /** @deprecated */ ;

    static class A { // violation 'Missing a Javadoc comment.'

    }

}

