package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.lang.annotation.Documented;

@SuppressWarnings(value={"unchecked", "unused"}) // violation
public class InputSuppressWarningsExpanded5
{
    @SuppressWarnings(value={"   "})
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
            Object o = new InputSuppressWarningsExpanded5() {

                @Override
                @SuppressWarnings(value={"unchecked"}) // violation
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

        @SuppressWarnings(value={"unused", "bleh"}) // violation
        int cool();
    }

    public class Junk {

        @SuppressWarnings(value={})
        int a = 1;

        @SuppressWarnings(value={"unchecked"}) // violation
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings(value={"unchecked"})String y) { // violation

        }
    }

}
