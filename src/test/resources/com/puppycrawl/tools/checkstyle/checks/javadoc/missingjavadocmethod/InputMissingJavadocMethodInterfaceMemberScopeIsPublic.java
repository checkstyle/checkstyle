/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = (default)public
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
ignoreMethodsWithImplementation = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public interface InputMissingJavadocMethodInterfaceMemberScopeIsPublic {

    enum Enum {

        ;

        public static void method() {} // violation 'Missing a Javadoc comment.'

        void packagePrivateMethod() {}

    }

    class Class {

        public void method() {} // violation 'Missing a Javadoc comment.'

        void packagePrivateMethod() {}

    }

}
