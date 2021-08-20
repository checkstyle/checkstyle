/*
SuppressWarnings
format = un.*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.lang.annotation.Documented;

@SuppressWarnings({"unchecked", "unused"}) // 2 violations
public class InputSuppressWarningsCompact5
{
    @SuppressWarnings({"   "})
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
            Object o = new InputSuppressWarningsCompact5() {

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
