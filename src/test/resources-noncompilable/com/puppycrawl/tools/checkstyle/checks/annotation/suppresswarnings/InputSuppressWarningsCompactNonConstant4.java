/*
SuppressWarnings
format = un.*
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
public class InputSuppressWarningsCompactNonConstant4 {
    @SuppressWarnings({"   "})
    class Empty {
        // violation below 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings({"unchecked", ""})
        public Empty() {

        }
    }

    // violation below 'The warning 'unused' cannot be suppressed at this location'
    @SuppressWarnings({"unused"})
    enum Duh {

        // violation below 'The warning 'unforgiven' cannot be suppressed at this location'
        @SuppressWarnings({"unforgiven", "    un"})
        D;

        public static void foo() {

            // violation below 'The warning 'unused' cannot be suppressed at this location'
            @SuppressWarnings({"unused"})
            Object o = new InputSuppressWarningsCompactNonConstant4() {

                @Override
                @SuppressWarnings({"unchecked"})
                // violation above 'The warning 'unchecked' cannot be suppressed at this location'
                public String toString() {
                    return "";
                }
            };
        }
    }

    @SuppressWarnings({"invalid"})
    @Documented
    @interface Sweet {
        int cool();
    }

    @Documented
    @SuppressWarnings({})
    @interface MoreSweetness {

        // violation below 'The warning 'unused' cannot be suppressed at this location'
        @SuppressWarnings({"unused", "ignore"})
        int cool();
    }

    public class Junk {
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
    // 2 violations 3 lines below:
    // 'cannot be suppressed at this location'
    // 'cannot be suppressed at this location'
    @SuppressWarnings({(false) ? "unchecked" : "", (false) ? "unchecked" : ""})
    class Cond {

        // violation below 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings({(false) ? "" : "unchecked"})
        public Cond() {}
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings({(false) ? (true) ? "   " : "unused" : "unchecked",
            (false) ? (true) ? "   " : "unused" : "unchecked"})
        // 2 violations above:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        public void aCond1() {}
        @SuppressWarnings({(false) ? "unchecked" : (true) ? "   " : "unused"})
        // 2 violations above:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        public void aCond2() {}
        // violation below 'The warning 'unchecked' cannot be suppressed at this location'
        @java.lang.SuppressWarnings({(false) ? "unchecked" :
                // violation below 'The warning 'unused' cannot be suppressed at this location'
                ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused",
                // violation below 'The warning 'unchecked' cannot be suppressed at this location'
            (false) ? "unchecked" : ("" == "") ? (false) ? (true) ? "" :
                    "foo" : "   " :
                    "unused"})
        // violation above 'The warning 'unused' cannot be suppressed at this location'
        public void seriously() {

        }
    }
}
