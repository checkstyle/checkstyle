//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 *
 * minLineCount = 2
 * allowedAnnotations = Override
 * scope = public
 * excludeScope = null
 * allowMissingPropertyJavadoc = false
 * ignoreMethodNamesRegex = null
 * tokens = {METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF}
 */
public class InputMissingJavadocMethodRecordsAndCtorsMinLineCount {
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
