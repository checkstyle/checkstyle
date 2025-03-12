/*
SuppressWarnings
format = ^unchecked$*
tokens = CLASS_DEF,METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.lang.annotation.Documented;

// violation below, 'The warning 'unchecked' cannot be suppressed at this location'
@SuppressWarnings("unchecked")
public class InputSuppressWarningsSingle4
{
    @SuppressWarnings("   ")
    class Empty {

        @SuppressWarnings("")
        public Empty() {

        }
    }

    @SuppressWarnings("unused")
    enum Duh {

        @SuppressWarnings("unforgiven")
        D;

        public static void foo() {

            @SuppressWarnings("unused")
            Object o = new InputSuppressWarningsSingle4() {

                @Override
                @SuppressWarnings("unchecked")
                // violation above, 'The warning 'unchecked' cannot be suppressed at this location'
                public String toString() {
                    return "";
                }
            };
        }
    }

    @SuppressWarnings("abcun")
    @Documented
    @interface Sweet {
        int cool();
    }

    @Documented
    @SuppressWarnings("abcun")
    @interface MoreSweetness {

        @SuppressWarnings("unused")
        int cool();
    }

    public class Junk {

        @SuppressWarnings("")
        int a = 1;

        @SuppressWarnings("unchecked")
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings("unchecked")String y) {

        }
    }

    // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
    @SuppressWarnings((false) ? "unchecked" : "")
    class Cond {

        @SuppressWarnings((false) ? "" : "unchecked")
        public Cond() {

        }

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings((false) ? (true) ? "   " : "unused" : "unchecked")
        public void aCond1() {

        }

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings((false) ? "unchecked" : (true) ? "   " : "unused")
        public void aCond2() {

        }

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
        @java.lang.SuppressWarnings((false) ? "unchecked" :
                ("" == "") ? (false) ? (true) ? "" : "foo" : "    " : "unused")
        public void seriously() {

        }
    }
}
