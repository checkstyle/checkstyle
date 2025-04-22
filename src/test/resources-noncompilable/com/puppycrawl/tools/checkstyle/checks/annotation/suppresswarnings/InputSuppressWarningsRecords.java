/*
SuppressWarnings
format = ^unchecked$*|^unused$*|.*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.lang.annotation.Documented;
import java.util.HashMap;

/* Config:
 *
 * format = "^unchecked$*|^unused$*|.*"
 * tokens = {CLASS_DEF , INTERFACE_DEF , ENUM_DEF , ANNOTATION_DEF ,
 * ANNOTATION_FIELD_DEF , ENUM_CONSTANT_DEF , PARAMETER_DEF ,
 * VARIABLE_DEF , METHOD_DEF , CTOR_DEF, RECORD_DEF, COMPACT_CTOR_DEF}
 */
@SuppressWarnings(value = {"unchecked", "unused"}) // 2 violations
public record InputSuppressWarningsRecords
        (String x) {

    @SuppressWarnings(value = {"unchecked", ""}) // 2 violations
    public InputSuppressWarningsRecords{}
    // violation below, 'The warning '   ' cannot be suppressed at this location'
    @SuppressWarnings(value = {"   "})
    class Empty {

        @SuppressWarnings(value = {"unchecked", ""}) // 2 violations
        public Empty() {

        }
    }

    // violation below, 'The warning 'unused' cannot be suppressed at this location'
    @SuppressWarnings(value = {"unused"})
    enum Duh {

        @SuppressWarnings(value = {"unforgiven", "    un"}) // 2 violations
        D;

        public static void foo() {

            Object myHashMap;
            // violation below, 'The warning 'unused' cannot be suppressed at this location'
            @SuppressWarnings(value = {"unused"}) int x = 42;

        }
    }

    // violation below, 'The warning 'abcun' cannot be suppressed at this location'
    @SuppressWarnings(value = {"abcun"})
    @Documented
    @interface inter {
        int cool();
    }

    @Documented
    @SuppressWarnings(value = {})
    // violation above, 'The warning '' cannot be suppressed at this location'
    @interface MoreSweetness {

        @SuppressWarnings(value = {"unused", "something else"}) // 2 violations
        int cool();
    }

    public record MyRecord() {

        // violation below, 'The warning '' cannot be suppressed at this location'
        @SuppressWarnings(value = {})
        static int a = 1;

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
        @SuppressWarnings(value = {"unchecked"})
        @Deprecated
        static int b = 1;

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location'
        void doFoo(String s, @SuppressWarnings(value = {"unchecked"}) String y) {

        }
    }

}
