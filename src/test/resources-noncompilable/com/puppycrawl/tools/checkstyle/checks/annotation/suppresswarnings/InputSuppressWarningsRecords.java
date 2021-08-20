/*
SuppressWarnings
format = ^unchecked$*|^unused$*|.*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

//non-compiled with javac: Compilable with Java14
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
    @SuppressWarnings(value = {"   "}) // violation
    class Empty {

        @SuppressWarnings(value = {"unchecked", ""}) // 2 violations
        public Empty() {

        }
    }

    @SuppressWarnings(value = {"unused"}) // violation
    enum Duh {

        @SuppressWarnings(value = {"unforgiven", "    un"}) // 2 violations
        D;

        public static void foo() {

            Object myHashMap;
            @SuppressWarnings(value = {"unused"}) int x = 42;  // violation

        }
    }

    @SuppressWarnings(value = {"abcun"}) // violation
    @Documented
    @interface inter {
        int cool();
    }

    @Documented
    @SuppressWarnings(value = {}) // violation
    @interface MoreSweetness {

        @SuppressWarnings(value = {"unused", "something else"}) // 2 violations
        int cool();
    }

    public record MyRecord() {

        @SuppressWarnings(value = {}) // violation
        static int a = 1;

        @SuppressWarnings(value = {"unchecked"}) // violation
        @Deprecated
        static int b = 1;

        void doFoo(String s, @SuppressWarnings(value = {"unchecked"}) String y) { // violation

        }
    }

}
