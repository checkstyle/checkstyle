/*
MissingJavadocMethod
allowMissingPropertyJavadoc = (default)false
allowedAnnotations = (default)Override
excludeScope = (default)null
ignoreMethodNamesRegex = (default)null
ignoreMethodsWithImplementation = true
minLineCount = (default)-1
scope = private
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodIgnoreMethodsWithImplementation {
    public void concreteMethod() { // ok, has implementation
    }
    abstract class AbstractInner {
        public abstract void abstractMethod(); // violation

        public void concreteInAbstractClass() { // ok, has implementation
        }

        /** documented */
        public void documentedMethod() { // ok, valid
        }
    }
    interface InnerInterface {
        void interfaceMethod(); // violation
    }
    enum TestEnum {
        A;
        void enumMethod() {} // ok, has implementation
    }
    record TestRecord() {
        public void recordMethod() {} // ok, has implementation
        public TestRecord {} // ok, has implementation
    }
    @interface TestAnnotation {
        String value(); // violation
    }
}
