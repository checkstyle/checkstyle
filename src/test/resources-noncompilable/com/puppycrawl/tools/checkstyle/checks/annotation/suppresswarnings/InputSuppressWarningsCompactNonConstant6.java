//non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
import java.lang.annotation.Documented;

@SuppressWarnings({"unchecked", "unused"}) // violation
public class InputSuppressWarningsCompactNonConstant6
{
    @SuppressWarnings({"   "}) // violation
    class Empty {

        @SuppressWarnings({"unchecked", ""}) // violation
        public Empty() {

        }
    }

    @SuppressWarnings({"unused"}) // violation
    enum Duh {

        @SuppressWarnings({"unforgiven", "    un"}) // violation
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

        @SuppressWarnings({"unused", "bleh"}) // violation
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

    @SuppressWarnings({(false) ? "unchecked" : "", (false) ? "unchecked" : ""}) // violation
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

        @java.lang.SuppressWarnings({(false) ? "unchecked" : // violation
                ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused", // violation
            (false) ? "unchecked" : ("" == "") ? (false) ? (true) ? "" : // violation
                    "foo" : "   " : // violation
                    "unused"}) // violation
        public void seriously() {

        }
    }
}
