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
@SuppressWarnings(value = {"unchecked", "unused"})
public record InputSuppressWarningsRecords
        (String x) {
    // 2 violations 3 lines below:
    // 'cannot be suppressed at this location'
    // 'cannot be suppressed at this location'
    @SuppressWarnings(value = {"unchecked", ""})
    public InputSuppressWarningsRecords{}
    // violation below 'The warning '   ' cannot be suppressed at this location'
    @SuppressWarnings(value = {"   "})
    class Empty {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings(value = {"unchecked", ""})
        public Empty() {

        }
    }

    // violation below 'The warning 'unused' cannot be suppressed at this location'
    @SuppressWarnings(value = {"unused"})
    enum Duh {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings(value = {"unforgiven", "    un"})
        D;

        public static void foo() {

            Object myHashMap;
            // violation below 'The warning 'unused' cannot be suppressed at this location'
            @SuppressWarnings(value = {"unused"}) int x = 42;

        }
    }

    // violation below 'The warning 'invalid' cannot be suppressed at this location'
    @SuppressWarnings(value = {"invalid"})
    @Documented
    @interface inter {
        int cool();
    }

    @Documented
    @SuppressWarnings(value = {})
    // violation above 'The warning '' cannot be suppressed at this location'
    @interface MoreSweetness {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings(value = {"unused", "something else"})
        int cool();
    }

    public record MyRecord() {

        // violation below 'The warning '' cannot be suppressed at this location'
        @SuppressWarnings(value = {})
        static int a = 1;

        // violation below 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings(value = {"unchecked"})
        @Deprecated
        static int b = 1;

        // violation below 'The warning 'unchecked' cannot be suppressed at this location'
        void doFoo(String s, @SuppressWarnings(value = {"unchecked"}) String y) {

        }
    }

}
