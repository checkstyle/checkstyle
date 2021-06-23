package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.lang.annotation.Documented;

@SuppressWarnings("unchecked") // violation
public class InputSuppressWarningsSingle3
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
            Object o = new InputSuppressWarningsSingle3() {

                @Override
                @SuppressWarnings("unchecked") // violation
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

        @SuppressWarnings("unchecked") // violation
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings("unchecked")String y) { // violation

        }
    }

    @SuppressWarnings((false) ? "unchecked" : "") // violation
    class Cond {

        @SuppressWarnings((false) ? "" : "unchecked") // violation
        public Cond() {

        }

        @SuppressWarnings((false) ? (true) ? "   " : "unused" : "unchecked") // violation
        public void aCond1() {

        }

        @SuppressWarnings((false) ? "unchecked" : (true) ? "   " : "unused") // violation
        public void aCond2() {

        }

        @java.lang.SuppressWarnings((false) ? "unchecked" : // violation
                ("" == "") ? (false) ? (true) ? "" : "foo" : "    " : "unused")
        public void seriously() {

        }
    }
}
