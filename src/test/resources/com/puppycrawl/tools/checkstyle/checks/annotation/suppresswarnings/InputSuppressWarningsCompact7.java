/*
SuppressWarnings
format = ^unchecked$*|^unused$*|.*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF, PATTERN_VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.lang.annotation.Documented;
// 2 violations 3 lines below:
// 'cannot be suppressed at this location'
// 'cannot be suppressed at this location'
@SuppressWarnings({"unchecked", "unused"})
public class InputSuppressWarningsCompact7 {
    // violation below 'The warning '   ' cannot be suppressed at this location'
    @SuppressWarnings({"   "})
    class Empty {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings({"unchecked", ""})
        public Empty() {}
    }

    // violation below 'The warning 'unused' cannot be suppressed at this location'
    @SuppressWarnings({"unused"})
    enum Duh {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings({"unforgiven", "    un"})
        D;
        public static void foo() {
            // violation below 'The warning 'unused' cannot be suppressed at this location'
            @SuppressWarnings({"unused"})
            Object o = new InputSuppressWarningsCompact7() {
                @Override
                @SuppressWarnings({"unchecked"})
                // violation above 'The warning 'unchecked' cannot be suppressed at this location'
                public String toString() {
                    return "";
                }
            };
        }
    }

    // violation below 'The warning 'invalid' cannot be suppressed at this location'
    @SuppressWarnings({"invalid"})
    @Documented
    @interface Sweet {
        int cool();
    }

    @Documented
    @SuppressWarnings({})
    // violation above 'The warning '' cannot be suppressed at this location'
    @interface MoreSweetness {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings({"unused", "ignore"})
        int cool();
    }
    public class Junk {
        // violation below 'The warning '' cannot be suppressed at this location'
        @SuppressWarnings({})
        int a = 1;

        // violation below 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings({"unchecked"})
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings({"unchecked"})String y) {
            // violation above 'The warning 'unchecked' cannot be suppressed at this location'

        }
    }

}
