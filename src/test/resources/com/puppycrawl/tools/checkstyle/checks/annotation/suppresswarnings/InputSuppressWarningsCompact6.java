package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.lang.annotation.Documented;

@SuppressWarnings({"unchecked", "unused"}) // violation
public class InputSuppressWarningsCompact6
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
            Object o = new InputSuppressWarningsCompact6() {

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

}
