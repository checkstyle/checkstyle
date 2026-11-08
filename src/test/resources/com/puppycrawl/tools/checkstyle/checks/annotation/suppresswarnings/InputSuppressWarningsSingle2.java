/*
SuppressWarnings
format = .*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF, PATTERN_VARIABLE_DEF, MODULE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.lang.annotation.Documented;

// violation below 'The warning 'unchecked' cannot be suppressed at this location'
@SuppressWarnings("unchecked")
public class InputSuppressWarningsSingle2
{
    // violation below 'The warning '   ' cannot be suppressed at this location'
    @SuppressWarnings("   ")
    class Empty {

        // violation below 'The warning '' cannot be suppressed at this location'
        @SuppressWarnings("")
        public Empty() {

        }
    }

    // violation below 'The warning 'unused' cannot be suppressed at this location'
    @SuppressWarnings("unused")
    enum Duh {

        // violation below 'The warning 'unforgiven' cannot be suppressed at this location'
        @SuppressWarnings("unforgiven")
        D;

        public static void foo() {

            // violation below 'The warning 'unused' cannot be suppressed at this location'
            @SuppressWarnings("unused")
            Object o = new InputSuppressWarningsSingle2() {

                // violation 2 lines below 'The warning 'unchecked' cannot be suppressed'
                @Override
                @SuppressWarnings("unchecked")
                public String toString() {
                    return "";
                }
            };
        }
    }

    // violation below 'The warning 'invalid' cannot be suppressed at this location'
    @SuppressWarnings("invalid")
    @Documented
    @interface Sweet {
        int cool();
    }

    // violation 2 lines below 'The warning 'invalid' cannot be suppressed at this location'
    @Documented
    @SuppressWarnings("invalid")
    @interface MoreSweetness {

        // violation below 'The warning 'unused' cannot be suppressed at this location'
        @SuppressWarnings("unused")
        int cool();
    }

    public class Junk {

        // violation below 'The warning '' cannot be suppressed at this location'
        @SuppressWarnings("")
        int a = 1;

        // violation below 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings("unchecked")
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings("unchecked")String y) {
        // violation above 'The warning 'unchecked' cannot be suppressed at this location'
        }
    }
}
