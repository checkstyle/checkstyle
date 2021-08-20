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

        @SuppressWarnings(value={"unchecked", ""}) // violation
        public Empty() {

        }
    }

    @SuppressWarnings(value={"unused"}) // violation
    enum Duh {

        @SuppressWarnings(value={"unforgiven", "    un"}) // violation
        D;

        public static void foo() {

            @SuppressWarnings(value={"unused"}) // violation
            Object o = new InputSuppressWarningsExpandedNonConstant5() {

                @Override
                @SuppressWarnings(value={"unchecked"}) // violation
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

        @SuppressWarnings(value={"unused", "bleh"}) // violation
        int cool();
    }

    public class Junk {

        @SuppressWarnings(value={})
        int a = 1;

        @SuppressWarnings(value={"unchecked"}) // violation
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings(value={"unchecked"})String y) { // violation

        }
    }

    @SuppressWarnings(value={(false) ? "unchecked" : "", (false) ? "unchecked" : ""}) // violation
    class Cond { // violation above

        @SuppressWarnings(value={(false) ? "" : "unchecked"}) // violation
        public Cond() {

        }

        @SuppressWarnings(value={(false) ? (true) ? "   " : "unused" : "unchecked", // 2 violations
            (false) ? (true) ? "   " : "unused" : "unchecked"}) // 2 violations
        public void aCond1() {

        }

        @SuppressWarnings(value={(false) ? "unchecked" : (true) ? "   " : "unused"}) // 2 violations
        public void aCond2() {

        }

        @java.lang.SuppressWarnings(value={(false) ? "unchecked" : // violation
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused", // violation
                (false) ? "unchecked" : // violation
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused"}) // violation
        public void seriously() {

        }
    }
}
