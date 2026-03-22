/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = package
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public enum InputMissingJavadocMethodEnumCtorScopeIsPrivate {

    CONSTANT(0);

    private final int value;

    InputMissingJavadocMethodEnumCtorScopeIsPrivate(int value) {
        this.value = value;
    }

    void packagePrivateMethod() { // violation
    }

}
