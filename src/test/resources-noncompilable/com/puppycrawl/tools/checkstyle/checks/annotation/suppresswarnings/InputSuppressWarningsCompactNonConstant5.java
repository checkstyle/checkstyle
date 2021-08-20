/*
SuppressWarnings
format = ^unchecked$*|^unused$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

//non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
import java.lang.annotation.Documented;

@SuppressWarnings({"unchecked", "unused"}) // 2 violations
public class InputSuppressWarningsCompactNonConstant5
{
    @SuppressWarnings({"   "})
    class Empty {

        @SuppressWarnings({"unchecked", ""}) // violation
        public Empty() {

        }
    }

    @SuppressWarnings({"unused"}) // violation
    enum Duh {

        @SuppressWarnings({"unforgiven", "    un"})
        D;

        public static void foo() {

            @SuppressWarnings({"unused"}) // violation
            Object o = new InputSuppressWarningsCompactNonConstant5() {

                @Override
                @SuppressWarnings({"unchecked"}) // violation
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
    @SuppressWarnings({})
    @interface MoreSweetness {

        @SuppressWarnings({"unused", "bleh"}) // violation
        int cool();
    }

    public class Junk {

        @SuppressWarnings({})
        int a = 1;

        @SuppressWarnings({"unchecked"}) // violation
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings({"unchecked"})String y) { // violation

        }
    }

    @SuppressWarnings({(false) ? "unchecked" : "", (false) ? "unchecked" : ""}) // 2 violations
    class Cond {

        @SuppressWarnings({(false) ? "" : "unchecked"}) // violation
        public Cond() {

        }

        @SuppressWarnings({(false) ? (true) ? "   " : "unused" : "unchecked", // 2 violations
            (false) ? (true) ? "   " : "unused" : "unchecked"}) // 2 violations
        public void aCond1() {

        }

        @SuppressWarnings({(false) ? "unchecked" : (true) ? "   " : "unused"}) // 2 violations
        public void aCond2() {

        }

        @java.lang.SuppressWarnings({(false) ? "unchecked" : // violation
                ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused", // violation
            (false) ? "unchecked" : ("" == "") ? (false) ? (true) ? "" : // violation
                    "foo" : "   " :
                    "unused"}) // violation
        public void seriously() {

        }
    }
}
