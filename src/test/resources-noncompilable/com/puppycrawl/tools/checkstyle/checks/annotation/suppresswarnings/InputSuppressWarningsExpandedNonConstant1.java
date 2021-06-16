//non-compiled with eclipse: non-compilable annotation, for testing
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
import java.lang.annotation.Documented;

@SuppressWarnings(value={"unchecked", "unused"})
public class InputSuppressWarningsExpandedNonConstant1
{
    @SuppressWarnings(value={"   "}) // violation
    class Empty {

        @SuppressWarnings(value={"unchecked", ""}) // violation
        public Empty() {

        }
    }

    @SuppressWarnings(value={"unused"})
    enum Duh {

        @SuppressWarnings(value={"unforgiven", "    un"})
        D;

        public static void foo() {

            @SuppressWarnings(value={"unused"})
            Object o = new InputSuppressWarningsExpandedNonConstant1() {

                @Override
                @SuppressWarnings(value={"unchecked"})
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
    @SuppressWarnings(value={}) // violation
    @interface MoreSweetness {

        @SuppressWarnings(value={"unused", "bleh"})
        int cool();
    }

    public class Junk {

        @SuppressWarnings(value={}) // violation
        int a = 1;

        @SuppressWarnings(value={"unchecked"})
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings(value={"unchecked"})String y) {

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

        @java.lang.SuppressWarnings(value={(false) ? "unchecked" :
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused", // violation
                (false) ? "unchecked" :
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused"}) // violation
        public void seriously() {

        }
    }
}
