/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = (default)public
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodRecordsAndCtors {
    public record MyRecord(Integer number) {
        private static int mNumber;

        // not javadoc
        public void setNumber(final int number) { // violation
            mNumber = number;
        }

        // not javadoc
        public int getNumber() { // violation
            return mNumber;
        }

        public void setNumber1() { // violation
            mNumber = mNumber;
        }
    }

    public record MySecondRecord() {
        // not a javadoc comment on compact ctor
        public MySecondRecord { // violation
        }
    }

    public record MyThirdRecord() {
        // not a javadoc comment on ctor
        public MyThirdRecord() { // violation
        }
    }

    public void setNumber1() // violation
    {

    }
}
