/*
SuppressWarnings
format = ^unchecked$*|^unused$*|.*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF, PATTERN_VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.lang.annotation.Documented;

// violation below 'The warning 'unchecked' cannot be suppressed at this location'
@SuppressWarnings("unchecked")
public class InputSuppressWarningsSingle7
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
            Object o = new InputSuppressWarningsSingle7() {

                @Override
                @SuppressWarnings("unchecked")
                // violation above 'The warning 'unchecked' cannot be suppressed at this location'
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

    @Documented
    @SuppressWarnings("invalid")
    // violation above 'The warning 'invalid' cannot be suppressed at this location'
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
    // 2 violations 3 lines below:
    // 'cannot be suppressed at this location'
    // 'cannot be suppressed at this location'
    @SuppressWarnings((false) ? "unchecked" : "")
    class Cond {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings((false) ? "" : "unchecked")
        public Cond() {

        }
        // 3 violations 4 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings((false) ? (true) ? "   " : "unused" : "unchecked")
        public void aCond1() {

        }
        // 3 violations 4 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings((false) ? "unchecked" : (true) ? "   " : "unused")
        public void aCond2() {

        }

        // violation below 'The warning 'unchecked' cannot be suppressed at this location'
        @java.lang.SuppressWarnings((false) ? "unchecked" :
                ("" == "") ? (false) ? (true) ? "" : "foo" : "    " : "unused")
        public void seriously() {
            // 4 violations 2 lines above:
            // 'cannot be suppressed at this location'
            // 'cannot be suppressed at this location'
            // 'cannot be suppressed at this location'
            // 'cannot be suppressed at this location'
        }
    }
}
