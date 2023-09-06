/*
SuppressWarnings
format = ^unchecked$*|^unused$*|.*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

//non-compiled with eclipse: non-compilable annotation, for testing
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
import java.lang.annotation.Documented;

@SuppressWarnings(value={"unchecked", "unused"}) // 2 violations
public class InputSuppressWarningsExpandedNonConstant7
{
    @SuppressWarnings(value={"   "}) // violation
    class Empty {

        @SuppressWarnings(value={"unchecked", ""}) // 2 violations
        public Empty() {

        }
    }

    @SuppressWarnings(value={"unused"}) // violation
    enum Duh {

        @SuppressWarnings(value={"unforgiven", "    un"}) // 2 violations
        D;

        public static void foo() {

            @SuppressWarnings(value={"unused"}) // violation
            Object o = new InputSuppressWarningsExpandedNonConstant7() {

                @Override
                @SuppressWarnings(value={"unchecked"}) // violation
                public String toString() {
                    return "";
                }
            };
        }
    }

    @SuppressWarnings(value={"abcun"}) // violation
    @Documented
    @interface Sweet {
        int cool();
    }

    @Documented
    @SuppressWarnings(value={}) // violation
    @interface MoreSweetness {

        @SuppressWarnings(value={"unused", "bleh"}) // 2 violations
        int cool();
    }

    public class Junk {

        @SuppressWarnings(value={}) // violation
        int a = 1;

        @SuppressWarnings(value={"unchecked"}) // violation
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings(value={"unchecked"})String y) { // violation

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

        @java.lang.SuppressWarnings(value={(false) ? "unchecked" : // violation
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused", // 4 violations
                (false) ? "unchecked" : // violation
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused"}) // 4 violations
        public void seriously() {

        }
    }
}
