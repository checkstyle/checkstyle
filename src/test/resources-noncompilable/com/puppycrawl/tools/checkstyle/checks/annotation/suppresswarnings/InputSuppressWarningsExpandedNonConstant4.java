/*
SuppressWarnings
format = ^unchecked$*
tokens = CLASS_DEF


*/

//non-compiled with eclipse: non-compilable annotation, for testing
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
import java.lang.annotation.Documented;

// violation below, 'The warning 'unchecked' cannot be suppressed at this location'
@SuppressWarnings(value={"unchecked", "unused"})
public class InputSuppressWarningsExpandedNonConstant4
{
    @SuppressWarnings(value={"   "})
    class Empty {

        @SuppressWarnings(value={"unchecked", ""})
        public Empty() {

        }
    }

    @SuppressWarnings(value={"unused"})
    enum Duh {

        @SuppressWarnings(value={"unforgiven", "    un"})
        D;

        public static void foo() {

            @SuppressWarnings(value={"unused"})
            Object o = new InputSuppressWarningsExpandedNonConstant4() {

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
    @SuppressWarnings(value={})
    @interface MoreSweetness {

        @SuppressWarnings(value={"unused", "bleh"})
        int cool();
    }

    public class Junk {

        @SuppressWarnings(value={})
        int a = 1;

        @SuppressWarnings(value={"unchecked"})
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings(value={"unchecked"})String y) {

        }
    }

    // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
    @SuppressWarnings(value={(false) ? "unchecked" : "", (false) ? "unchecked" : ""})
    // violation above, 'The warning 'unchecked' cannot be suppressed at this location'
    class Cond {

        @SuppressWarnings(value={(false) ? "" : "unchecked"})
        public Cond() {

        }

        @SuppressWarnings(value={(false) ? (true) ? "   " : "unused" : "unchecked",
            (false) ? (true) ? "   " : "unused" : "unchecked"})
        public void aCond1() {

        }

        @SuppressWarnings(value={(false) ? "unchecked" : (true) ? "   " : "unused"})
        public void aCond2() {

        }

        @java.lang.SuppressWarnings(value={(false) ? "unchecked" :
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused",
                (false) ? "unchecked" :
                    ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused"})
        public void seriously() {

        }
    }
}
