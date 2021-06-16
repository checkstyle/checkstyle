//non-compiled with eclipse: non-compilable annotation, for testing
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
import java.lang.annotation.Documented;

@SuppressWarnings(value={"unchecked", "unused"}) // violation
public class InputSuppressWarningsExpandedNonConstant2
{
    @SuppressWarnings(value={"   "}) // violation
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
            Object o = new InputSuppressWarningsExpandedNonConstant2() {

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

        @SuppressWarnings(value={"unused", "bleh"}) // violation
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

    @SuppressWarnings(value={(false) ? "unchecked" : "", (false) ? "unchecked" : ""}) // violation
    class Cond {

        @SuppressWarnings(value={(false) ? "" : "unchecked"}) // violation
        public Cond() {

        }

        @SuppressWarnings(value={(false) ? (true) ? "   " : "unused" : "unchecked", // violation
            (false) ? (true) ? "   " : "unused" : "unchecked"}) // violation
        public void aCond1() {

        }

        @SuppressWarnings(value={(false) ? "unchecked" : (true) ? "   " : "unused"}) // violation
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
