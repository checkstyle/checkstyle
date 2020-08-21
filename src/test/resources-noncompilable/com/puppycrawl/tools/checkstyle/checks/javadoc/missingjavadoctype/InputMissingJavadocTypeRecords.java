//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

/* Config:
 *
 * scope = public
 * excludeScope = null
 * skipAnnotations = Generated
 * tokens = {INTERFACE_DEF , CLASS_DEF , ENUM_DEF , ANNOTATION_DEF, RECORD_DEF}
 */
public record InputMissingJavadocTypeRecords() { // violation
    class TestClass { // violation
    }

    //no javadoc here
    record MyRecord1() { // violation

    }

    record MyRecord2(String myString) { // violation

        public MyRecord2 {
        }
    }

    @NonNull1
    record MyRecord3(int x) { // ok due to annotation
        protected record MyRecord4(int y) { // violation
            private record MyRecord5(int z) { // violation

            }


        }
    }
}

@interface NonNull1{} // violation
