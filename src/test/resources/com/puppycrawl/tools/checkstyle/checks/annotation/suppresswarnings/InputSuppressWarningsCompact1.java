/*
SuppressWarnings
format = (default)^\\s*+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.lang.annotation.Documented;

@SuppressWarnings({"unchecked", "unused"})
public class InputSuppressWarningsCompact1
{
    // violation below, 'The warning '   ' cannot be suppressed at this location'
    @SuppressWarnings({"   "})
    class Empty {

        // violation below, 'The warning '' cannot be suppressed at this location'
        @SuppressWarnings({"unchecked", ""})
        public Empty() {

        }
    }

    @SuppressWarnings({"unused"})
    enum Duh {

        @SuppressWarnings({"unforgiven", "    un"})
        D;

        public static void foo() {

            @SuppressWarnings({"unused"})
            Object o = new InputSuppressWarningsCompact1() {

                @Override
                @SuppressWarnings({"unchecked"})
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
    // violation above, 'The warning '' cannot be suppressed at this location'
    @interface MoreSweetness {

        @SuppressWarnings({"unused", "bleh"})
        int cool();
    }

    public class Junk {

        // violation below, 'The warning '' cannot be suppressed at this location'
        @SuppressWarnings({})
        int a = 1;

        @SuppressWarnings({"unchecked"})
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings({"unchecked"})String y) {

        }
    }

}
