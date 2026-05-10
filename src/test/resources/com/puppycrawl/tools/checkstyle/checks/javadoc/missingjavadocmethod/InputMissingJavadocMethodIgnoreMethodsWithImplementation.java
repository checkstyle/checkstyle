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
    public void concreteMethod() {
    }
    abstract class AbstractInner {
        public abstract void abstractMethod(); // violation 'Missing a Javadoc comment'

        public void concreteInAbstractClass() {
        }

        /** documented */
        public void documentedMethod() {
        }
    }
    interface InnerInterface {
        void interfaceMethod(); // violation 'Missing a Javadoc comment'
    }
    enum TestEnum {
        A;
        void enumMethod() {}
    }
    record TestRecord() {
        public void recordMethod() {}
        public TestRecord {}
    }
    @interface TestAnnotation {
        String value(); // violation 'Missing a Javadoc comment'
    }
}
