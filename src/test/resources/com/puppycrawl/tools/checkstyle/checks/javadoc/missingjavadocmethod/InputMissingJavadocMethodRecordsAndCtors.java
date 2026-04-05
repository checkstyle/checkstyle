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

// java17
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodRecordsAndCtors {
    public record MyRecord(Integer number) {
        private static int mNumber;

        // not javadoc
        public void setNumber(final int number) { // violation 'Missing a Javadoc comment.'
            mNumber = number;
        }

        // not javadoc
        public int getNumber() { // violation 'Missing a Javadoc comment.'
            return mNumber;
        }

        public void setNumber1() { // violation 'Missing a Javadoc comment.'
            mNumber = mNumber;
        }
    }

    public record MySecondRecord() {
        // not a javadoc comment on compact ctor
        public MySecondRecord { // violation 'Missing a Javadoc comment.'
        }
    }

    public record MyThirdRecord() {
        // not a javadoc comment on ctor
        public MyThirdRecord() { // violation 'Missing a Javadoc comment.'
        }
    }

    public void setNumber1() // violation 'Missing a Javadoc comment.'
    {

    }
}
