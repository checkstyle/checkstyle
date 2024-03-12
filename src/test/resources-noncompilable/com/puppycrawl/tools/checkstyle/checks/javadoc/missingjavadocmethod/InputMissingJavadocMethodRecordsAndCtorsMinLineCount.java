/*
MissingJavadocMethod
minLineCount = 2
allowedAnnotations = (default)Override
scope = (default)public
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodRecordsAndCtorsMinLineCount {
    public record MyRecord(Integer number) {
        private static int mNumber;

        // not javadoc
        public void setNumber(final int number) {
            mNumber = number;
        }

        // not javadoc
        public int getNumber() {
            return mNumber;
        }

        public void setNumber1() {
            mNumber = mNumber;
        }
    }

    public record MySecondRecord() {
        // not a javadoc comment on compact ctor
        public MySecondRecord {
        }
    }

    public record MyThirdRecord() {
        // not a javadoc comment on ctor
        public MyThirdRecord() {
        }
    }

    public void setNumber1()
    {

    }
}
