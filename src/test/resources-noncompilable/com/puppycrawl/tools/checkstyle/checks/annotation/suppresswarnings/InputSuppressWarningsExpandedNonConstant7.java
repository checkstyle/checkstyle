/*
SuppressWarnings
format = ^unchecked$*|^unused$*|.*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/
// non-compiled with javac: Compilable with Java21 individually
// non-compiled with eclipse: non-compilable annotation, for testing
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
import java.lang.annotation.Documented;
// 2 violations 3 lines below:
// 'cannot be suppressed at this location'
// 'cannot be suppressed at this location'
@SuppressWarnings(value={"unchecked", "unused"})
public class InputSuppressWarningsExpandedNonConstant7
{
    // violation below 'The warning '   ' cannot be suppressed at this location'
    @SuppressWarnings(value={"   "})
    class Empty {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings(value={"unchecked", ""})
        public Empty() {

        }
    }

    // violation below 'The warning 'unused' cannot be suppressed at this location'
    @SuppressWarnings(value={"unused"})
    enum Duh {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings(value={"unforgiven", "    un"})
        D;

        public static void foo() {

            // violation below 'The warning 'unused' cannot be suppressed at this location'
            @SuppressWarnings(value={"unused"})
            Object o = new InputSuppressWarningsExpandedNonConstant7() {

                @Override
                @SuppressWarnings(value={"unchecked"})
                // violation above 'The warning 'unchecked' cannot be suppressed at this location'
                public String toString() {
                    return "";
                }
            };
        }
    }

    // violation below 'The warning 'invalid' cannot be suppressed at this location'
    @SuppressWarnings(value={"invalid"})
    @Documented
    @interface Sweet {
        int cool();
    }

    @Documented
    @SuppressWarnings(value={})
    // violation above 'The warning '' cannot be suppressed at this location'
    @interface MoreSweetness {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings(value={"unused", "ignore"})
        int cool();
    }

    public class Junk {

        // violation below 'The warning '' cannot be suppressed at this location'
        @SuppressWarnings(value={})
        int a = 1;

        // violation below 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings(value={"unchecked"})
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings(value={"unchecked"})String y) {
            // violation above 'The warning 'unchecked' cannot be suppressed at this location'

        }
    }
    // 2 violations 3 lines below:
    // 'cannot be suppressed at this location'
    // 'cannot be suppressed at this location'
    @SuppressWarnings(value={(false) ? "unchecked" : "",
            (false) ? "unchecked" : ""})
    class Cond {
        // 2 violations 2 lines above:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'

        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings(value={(false) ? "" : "unchecked"})
        public Cond() {

        }
        // 3 violations 4 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings(value={(false) ? (true) ? "   " : "unused" : "unchecked",
            (false) ? (true) ? "   " : "unused" : "unchecked"})
        public void aCond1() {
            // 3 violations 2 lines above:
            // 'cannot be suppressed at this location'
            // 'cannot be suppressed at this location'
            // 'cannot be suppressed at this location'
        }
        // 3 violations 4 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings(value={(false) ? "unchecked" : (true) ? "   " : "unused"})
        public void aCond2() {

        }

        // violation 6 lines below 'The warning 'unchecked' cannot be suppressed at this location'
        // 4 violations 6 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @java.lang.SuppressWarnings(value={(false) ? "unchecked" :
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused",
                (false) ? "unchecked" :
                // violation above 'The warning 'unchecked' cannot be suppressed at this location'
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused"})
        public void seriously() {
            // 4 violations 2 lines above:
            // 'cannot be suppressed at this location'
            // 'cannot be suppressed at this location'
            // 'cannot be suppressed at this location'
            // 'cannot be suppressed at this location'
        }
    }
}
