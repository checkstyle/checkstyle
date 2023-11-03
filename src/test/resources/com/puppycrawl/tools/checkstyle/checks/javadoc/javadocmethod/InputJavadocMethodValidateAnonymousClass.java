/*
JavadocMethod
allowedAnnotations = MyAnnotation, Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
validateInAnonymousClasses = true
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/


package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodValidateAnonymousClass {

     Thread ob = new Thread(){

    /**
     *
     */
        void print(int a){} //violation 'Expected @param tag for 'a''

     /**
     *
     */
        void println(int a, int b){
             // violation 1 lines above 'Expected @param tag for 'a'.'
             // violation 2 lines above 'Expected @param tag for 'b'.'
        }

    };
}
