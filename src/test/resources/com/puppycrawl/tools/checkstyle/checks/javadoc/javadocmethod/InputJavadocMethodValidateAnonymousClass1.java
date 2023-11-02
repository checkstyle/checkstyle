/*
JavadocMethod
allowedAnnotations = MyAnnotation
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
validateAnonymousClasses = true
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF



*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodValidateAnonymousClass1 {

     private final Thread anonymousClassInField = new Thread() {
        @Override
        public void run() {
            publicMethod(null, null);
        }

        /**
         *
         */
        public String publicMethod(String a, String b) {
            // violation above '@return tag should be present and have description.'
            // violation 2 lines above 'Expected @param tag for 'a'.'
            // violation 3 lines above 'Expected @param tag for 'b'.'
            return null;
        }
    };

     public Thread anonymousClassInMethod() {
        return new Thread() {
            @Override
            public void run() {
                privateMethod(null, null);
            }

            /**
             *
             */
            private String privateMethod(String a, String b) {
                // violation above '@return tag should be present and have description.'
                // violation 2 lines above 'Expected @param tag for 'a'.'
                // violation 3 lines above 'Expected @param tag for 'b'.'
                return null;
            }
        };
    }

    static public void Sample(){
         Test1 ob = new Test1(){
             @Override
             public void displayInfo() {}


            /**
             *
             */
            public void print(){}; // ok, no parameters and return type.

            /**
             *
             */
            public void print(String a){};  // violation  'Expected @param tag for 'a'.'

         };
    }
}


abstract class Test1{

   private int age;

   private String name;

   public abstract void displayInfo();
}
