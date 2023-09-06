/*
SuppressWarnings
format = ^unchecked$*|^unused$*|.*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

//non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
import java.lang.annotation.Documented;

@SuppressWarnings({"unchecked", "unused"}) // 2 violations
public class InputSuppressWarningsCompactNonConstant6
{
    @SuppressWarnings({"   "}) // violation
    class Empty {

        @SuppressWarnings({"unchecked", ""}) // 2 violations
        public Empty() {

        }
    }

    @SuppressWarnings({"unused"}) // violation
    enum Duh {

        @SuppressWarnings({"unforgiven", "    un"}) // 2 violations
        D;

        public static void foo() {

            @SuppressWarnings({"unused"}) // violation
            Object o = new InputSuppressWarningsCompactNonConstant6() {

                @Override
                @SuppressWarnings({"unchecked"}) // violation
                public String toString() {
                    return "";
                }
            };
        }
    }

    @SuppressWarnings({"abcun"}) // violation
    @Documented
    @interface Sweet {
        int cool();
    }

    @Documented
    @SuppressWarnings({}) // violation
    @interface MoreSweetness {

        @SuppressWarnings({"unused", "bleh"}) // 2 violations
        int cool();
    }

    public class Junk {

        @SuppressWarnings({}) // violation
        int a = 1;

        @SuppressWarnings({"unchecked"}) // violation
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings({"unchecked"})String y) { // violation

        }
    }

    @SuppressWarnings({(false) ? "unchecked" : "", (false) ? "unchecked" : ""}) // 4 violations
    class Cond {

        @SuppressWarnings({(false) ? "" : "unchecked"}) // 2 violations
        public Cond() {

        }

        @SuppressWarnings({(false) ? (true) ? "   " : "unused" : "unchecked", // 3 violations
            (false) ? (true) ? "   " : "unused" : "unchecked"}) // 3 violations
        public void aCond1() {

        }

        @SuppressWarnings({(false) ? "unchecked" : (true) ? "   " : "unused"}) // 3 violations
        public void aCond2() {

        }

        @java.lang.SuppressWarnings({(false) ? "unchecked" : // violation
                ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused", // 4 violations
            (false) ? "unchecked" : ("" == "") ? (false) ? (true) ? "" : // 2 violations
                    "foo" : "   " : // 2 violations
                    "unused"}) // violation
        public void seriously() {

        }
    }
}
