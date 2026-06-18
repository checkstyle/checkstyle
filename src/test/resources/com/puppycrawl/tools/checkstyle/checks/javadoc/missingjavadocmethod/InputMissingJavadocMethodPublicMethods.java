/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = (default)public
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
ignoreMethodsWithImplementation = (default)false
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/* Config = default */
public class InputMissingJavadocMethodPublicMethods {

    public void annotation(final char @SomeAnnotation [] arg) {}
    // violation above 'Missing a Javadoc comment.'

    public @SomeAnnotation String @SomeAnnotation [] annotationInSignature() {
    // violation above 'Missing a Javadoc comment.'
        return new String[]{};
    }

    public static <T> T[] typeInSignature(T[] array) { // violation 'Missing a Javadoc comment.'
        return null;
    }
    public static <T> T[][] typeInSignature2(T[][] array) {
    // violation above 'Missing a Javadoc comment.'
        return null;
    }

    public static void main(String[] args) throws Exception {}
    // violation above 'Missing a Javadoc comment.'

    /**
     * Some javadoc
     */
    public void annotation1(final char @SomeAnnotation [] arg) {}

    /**
     * Some javadoc
     */
    public @SomeAnnotation String @SomeAnnotation [] annotationInSignature1() {
        return new String[]{};
    }

    /**
     * Some javadoc
     */
    public static <T> T[] typeInSignature3(T[] array) {
        return null;
    }

    /**
     * Some javadoc
     */
    public static <T> T[][] typeInSignature4(T[][] array) {
        return null;
    }

    /**
     * Some javadoc
     */
    public static void mainWithArgs(String[] args) throws Exception {}

    @Target(ElementType.TYPE_USE)
    @interface SomeAnnotation {
    }
}
