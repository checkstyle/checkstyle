/*
MissingJavadocMethod
allowMissingPropertyJavadoc = (default)false
allowedAnnotations = (default)Override
excludeScope = (default)null
ignoreMethodNamesRegex = (default)null
minLineCount = (default)-1
requireJavadocForAbstractOnly = true
scope = private
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodAbstractOnly {

    public void concreteMethod() { // ok, concrete class is not checked
    }

    abstract class AbstractInner {

        public abstract void abstractMethod(); // violation

        public void concreteInAbstractClass() { // violation
        }

        /** documented */
        public void documentedMethod() { // ok, valid
        }
    }

    interface InnerInterface {

        void interfaceMethod(); // violation
    }
}

    enum TestEnum {
        A;
        void enumMethod() {} // ok, valid
    }

    record TestRecord() {
        public void recordMethod() {} // ok, valid
        public TestRecord {} // ok, valid
    }

    @interface TestAnnotation {
        String value(); // ok, valid
    }
