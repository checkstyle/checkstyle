/*
SuppressWarnings
format = .*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF, PATTERN_VARIABLE_DEF


*/
// non-compiled with javac: Compilable with Java21 individually
// non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
import java.lang.annotation.Documented;
// 2 violations 3 lines below:
// 'cannot be suppressed at this location'
// 'cannot be suppressed at this location'
@SuppressWarnings({"unchecked", "unused"})
public class InputSuppressWarningsCompactNonConstant2
{
    // violation below 'The warning '   ' cannot be suppressed at this location'
    @SuppressWarnings({"   "})
    class Empty {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings({"unchecked", ""})
        public Empty() {

        }
    }

    // violation below 'The warning 'unused' cannot be suppressed at this location'
    @SuppressWarnings({"unused"})
    enum Duh {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings({"unforgiven", "    un"})
        D;

        public static void foo() {

            // violation below 'The warning 'unused' cannot be suppressed at this location'
            @SuppressWarnings({"unused"})
            Object o = new InputSuppressWarningsCompactNonConstant2() {

                @Override
                @SuppressWarnings({"unchecked"})
                // violation above 'The warning 'unchecked' cannot be suppressed at this location'
                public String toString() {
                    return "";
                }
            };
        }
    }

    // violation below 'The warning 'invalid' cannot be suppressed at this location'
    @SuppressWarnings({"invalid"})
    @Documented
    @interface Sweet {
        int cool();
    }

    @Documented
    @SuppressWarnings({})
    // violation above 'The warning '' cannot be suppressed at this location'
    @interface MoreSweetness {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings({"unused", "ignore"})
        int cool();
    }

    public class Junk {

        // violation below 'The warning '' cannot be suppressed at this location'
        @SuppressWarnings({})
        int a = 1;

        // violation below 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings({"unchecked"})
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings({"unchecked"})String y) {
            // violation above 'The warning 'unchecked' cannot be suppressed at this location'

        }
    }
    // 4 violations 5 lines below:
    // 'cannot be suppressed at this location'
    // 'cannot be suppressed at this location'
    // 'cannot be suppressed at this location'
    // 'cannot be suppressed at this location'
    @SuppressWarnings({(false) ? "unchecked" : "", (false) ? "unchecked" : ""})
    class Cond {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings({(false) ? "" : "unchecked"})
        public Cond() {

        }
        // 3 violations 4 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings({(false) ? (true) ? "   " : "unused" : "unchecked",
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
        @SuppressWarnings({(false) ? "unchecked" : (true) ? "   " : "unused"})
        public void aCond2() {

        }
        // violation 6 lines below 'The warning 'unchecked' cannot be suppressed at this location'
        // 4 violations 6 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @java.lang.SuppressWarnings({(false) ? "unchecked" :
                ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused",
            (false) ? "unchecked" : ("" == "") ? (false) ? (true) ? "" :
                    "foo" : "   " :
                    "unused"})
        // 2 violations 3 lines above:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 2 violations 5 lines above:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // violation 7 lines above 'The warning 'unused' cannot be suppressed at this location'
        public void seriously() {

        }
    }
}
