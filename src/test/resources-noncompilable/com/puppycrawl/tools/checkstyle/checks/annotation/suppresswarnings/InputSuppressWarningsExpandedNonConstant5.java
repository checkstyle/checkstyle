/*
SuppressWarnings
format = un.*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

//non-compiled with eclipse: non-compilable annotation, for testing
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
import java.lang.annotation.Documented;

@SuppressWarnings(value={"unchecked", "unused"}) // 2 violations
public class InputSuppressWarningsExpandedNonConstant5
{
    @SuppressWarnings(value={"   "})
    class Empty {

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings(value={"unchecked", ""})
        public Empty() {

        }
    }

    // violation below, 'The warning 'unused' cannot be suppressed at this location'
    @SuppressWarnings(value={"unused"})
    enum Duh {

        // violation below, 'The warning 'unforgiven' cannot be suppressed at this location'
        @SuppressWarnings(value={"unforgiven", "    un"})
        D;

        public static void foo() {

            // violation below, 'The warning 'unused' cannot be suppressed at this location'
            @SuppressWarnings(value={"unused"})
            Object o = new InputSuppressWarningsExpandedNonConstant5() {

                @Override
                @SuppressWarnings(value={"unchecked"})
                // violation above, 'The warning 'unchecked' cannot be suppressed at this location'
                public String toString() {
                    return "";
                }
            };
        }
    }

    @SuppressWarnings(value={"abcun"})
    @Documented
    @interface Sweet {
        int cool();
    }

    @Documented
    @SuppressWarnings(value={})
    @interface MoreSweetness {

        // violation below, 'The warning 'unused' cannot be suppressed at this location'
        @SuppressWarnings(value={"unused", "bleh"})
        int cool();
    }

    public class Junk {

        @SuppressWarnings(value={})
        int a = 1;

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings(value={"unchecked"})
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings(value={"unchecked"})String y) {
            // violation above, 'The warning 'unchecked' cannot be suppressed at this location'

        }
    }

    // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
    @SuppressWarnings(value={(false) ? "unchecked" : "", (false) ? "unchecked" : ""})
    // violation above, 'The warning 'unchecked' cannot be suppressed at this location'
    class Cond {

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings(value={(false) ? "" : "unchecked"})
        public Cond() {

        }

        @SuppressWarnings(value={(false) ? (true) ? "   " : "unused" : "unchecked", // 2 violations
            (false) ? (true) ? "   " : "unused" : "unchecked"}) // 2 violations
        public void aCond1() {

        }

        @SuppressWarnings(value={(false) ? "unchecked" : (true) ? "   " : "unused"}) // 2 violations
        public void aCond2() {

        }

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
        @java.lang.SuppressWarnings(value={(false) ? "unchecked" :
                // violation below, 'The warning 'unused' cannot be suppressed at this location'
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused",
                // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
                (false) ? "unchecked" :
                // violation below, 'The warning 'unused' cannot be suppressed at this location'
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused"})
        public void seriously() {

        }
    }
}
