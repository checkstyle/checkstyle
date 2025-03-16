/*
SuppressWarnings
format = ^unchecked$*|^unused$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.lang.annotation.Documented;

@SuppressWarnings(value={"unchecked", "unused"}) // 2 violations
public class InputSuppressWarningsExpanded6
{
    @SuppressWarnings(value={"   "})
    class Empty {

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings(value={"unchecked", ""})
        public Empty() {

        }
    }

    // violation below, 'The warning 'unused' cannot be suppressed at this location'
    @SuppressWarnings(value={"unused"})
    enum Duh {

        @SuppressWarnings(value={"unforgiven", "    un"})
        D;

        public static void foo() {

            // violation below, 'The warning 'unused' cannot be suppressed at this location'
            @SuppressWarnings(value={"unused"})
            Object o = new InputSuppressWarningsExpanded6() {

                @Override
                @SuppressWarnings(value={"unchecked"})
                // violation above, 'The warning 'unchecked' cannot be suppressed at this location'
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

        // violation below, 'The warning 'unused' cannot be suppressed at this location'
        @SuppressWarnings(value={"unused", "bleh"})
        int cool();
    }

    public class Junk {

        @SuppressWarnings(value={})
        int a = 1;

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings(value={"unchecked"})
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings(value={"unchecked"})String y) {
            // violation above, 'The warning 'unchecked' cannot be suppressed at this location'

        }
    }

}
