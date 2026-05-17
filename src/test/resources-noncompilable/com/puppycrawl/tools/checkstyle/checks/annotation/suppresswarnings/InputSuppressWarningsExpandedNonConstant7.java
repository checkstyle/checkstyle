/*
SuppressWarnings
format = ^unchecked$*|^unused$*|.*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

// non-compiled with eclipse: non-compilable annotation, for testing
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
import java.lang.annotation.Documented;

@SuppressWarnings(value={"unchecked", "unused"}) // 2 violations
public class InputSuppressWarningsExpandedNonConstant7
{
    // violation below, 'The warning '   ' cannot be suppressed at this location'
    @SuppressWarnings(value={"   "})
    class Empty {

        @SuppressWarnings(value={"unchecked", ""}) // 2 violations
        public Empty() {

        }
    }

    // violation below, 'The warning 'unused' cannot be suppressed at this location'
    @SuppressWarnings(value={"unused"})
    enum Duh {

        @SuppressWarnings(value={"unforgiven", "    un"}) // 2 violations
        D;

        public static void foo() {

            // violation below, 'The warning 'unused' cannot be suppressed at this location'
            @SuppressWarnings(value={"unused"})
            Object o = new InputSuppressWarningsExpandedNonConstant7() {

                // violation 2 lines below 'The warning 'unchecked' cannot be suppressed at this location'
                @Override
                @SuppressWarnings(value={"unchecked"})
                public String toString() {
                    return "";
                }
            };
        }
    }

    // violation below, 'The warning 'invalid' cannot be suppressed at this location'
    @SuppressWarnings(value={"invalid"})
    @Documented
    @interface Sweet {
        int cool();
    }

    // violation 2 lines below 'The warning '' cannot be suppressed at this location'
    @Documented
    @SuppressWarnings(value={})
    @interface MoreSweetness {

        @SuppressWarnings(value={"unused", "ignore"}) // 2 violations
        int cool();
    }

    public class Junk {

        // violation below, 'The warning '' cannot be suppressed at this location'
        @SuppressWarnings(value={})
        int a = 1;

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings(value={"unchecked"})
        @Deprecated
        int b = 1;
        // violation below 'The warning 'unchecked' cannot be suppressed at this location'
        void doFoo(String s, @SuppressWarnings(value={"unchecked"})String y) {

        }
    }

    @SuppressWarnings(value={(false) ? "unchecked" : "", // 2 violations
            (false) ? "unchecked" : ""}) // 2 violations
    class Cond {

        @SuppressWarnings(value={(false) ? "" : "unchecked"}) // 2 violations
        public Cond() {

        }

        @SuppressWarnings(value={(false) ? (true) ? "   " : "unused" : "unchecked", // 3 violations
            (false) ? (true) ? "   " : "unused" : "unchecked"}) // 3 violations
        public void aCond1() {

        }

        @SuppressWarnings(value={(false) ? "unchecked" : (true) ? "   " : "unused"}) // 3 violations
        public void aCond2() {

        }

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
        @java.lang.SuppressWarnings(value={(false) ? "unchecked" :
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused", // 4 violations
                (false) ? "unchecked" : // violation, 'The warning 'unchecked' cannot be suppressed at this location'
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused"}) // 4 violations
        public void seriously() {

        }
    }
}
