/*
SuppressWarnings
format = (default)^\s*+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

//non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
import java.lang.annotation.Documented;

@SuppressWarnings({"unchecked", "unused"})
public class InputSuppressWarningsCompactNonConstant1
{
    @SuppressWarnings({"   "}) // violation
    class Empty {

        @SuppressWarnings({"unchecked", ""}) // violation
        public Empty() {

        }
    }

    @SuppressWarnings({"unused"})
    enum Duh {

        @SuppressWarnings({"unforgiven", "    un"})
        D;

        public static void foo() {

            @SuppressWarnings({"unused"})
            Object o = new InputSuppressWarningsCompactNonConstant1() {

                @Override
                @SuppressWarnings({"unchecked"})
                public String toString() {
                    return "";
                }
            };
        }
    }

    @SuppressWarnings({"abcun"})
    @Documented
    @interface Sweet {
        int cool();
    }

    @Documented
    @SuppressWarnings({}) // violation
    @interface MoreSweetness {

        @SuppressWarnings({"unused", "bleh"})
        int cool();
    }

    public class Junk {

        @SuppressWarnings({}) // violation
        int a = 1;

        @SuppressWarnings({"unchecked"})
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings({"unchecked"})String y) {

        }
    }

    @SuppressWarnings({(false) ? "unchecked" : "", (false) ? "unchecked" : ""}) // 2 violations
    class Cond {

        @SuppressWarnings({(false) ? "" : "unchecked"}) // violation
        public Cond() {

        }

        @SuppressWarnings({(false) ? (true) ? "   " : "unused" : "unchecked", // violation
            (false) ? (true) ? "   " : "unused" : "unchecked"}) // violation
        public void aCond1() {

        }

        @SuppressWarnings({(false) ? "unchecked" : (true) ? "   " : "unused"}) // violation
        public void aCond2() {

        }

        @java.lang.SuppressWarnings({(false) ? "unchecked" :
                ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused", // 2 violations
            (false) ? "unchecked" : ("" == "") ? (false) ? (true) ? "" : // violation
                    "foo" : "   " : // violation
                    "unused"})
        public void seriously() {

        }
    }
}
