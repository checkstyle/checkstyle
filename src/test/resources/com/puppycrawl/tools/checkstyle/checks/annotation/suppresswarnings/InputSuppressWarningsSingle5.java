/*
SuppressWarnings
format = .*un.*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.lang.annotation.Documented;

@SuppressWarnings("unchecked") // violation
public class InputSuppressWarningsSingle5
{
    @SuppressWarnings("   ")
    class Empty {

        @SuppressWarnings("")
        public Empty() {

        }
    }

    @SuppressWarnings("unused") // violation
    enum Duh {

        @SuppressWarnings("unforgiven") // violation
        D;

        public static void foo() {

            @SuppressWarnings("unused") // violation
            Object o = new InputSuppressWarningsSingle5() {

                @Override
                @SuppressWarnings("unchecked") // violation
                public String toString() {
                    return "";
                }
            };
        }
    }

    @SuppressWarnings("abcun") // violation
    @Documented
    @interface Sweet {
        int cool();
    }

    @Documented
    @SuppressWarnings("abcun") // violation
    @interface MoreSweetness {

        @SuppressWarnings("unused") // violation
        int cool();
    }

    public class Junk {

        @SuppressWarnings("")
        int a = 1;

        @SuppressWarnings("unchecked") // violation
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings("unchecked")String y) { // violation

        }
    }

    @SuppressWarnings((false) ? "unchecked" : "") // violation
    class Cond {

        @SuppressWarnings((false) ? "" : "unchecked") // violation
        public Cond() {

        }

        @SuppressWarnings((false) ? (true) ? "   " : "unused" : "unchecked") // 2 violations
        public void aCond1() {

        }

        @SuppressWarnings((false) ? "unchecked" : (true) ? "   " : "unused") // 2 violations
        public void aCond2() {

        }

        @java.lang.SuppressWarnings((false) ? "unchecked" : // violation
                ("" == "") ? (false) ? (true) ? "" : "foo" : "    " : "unused") // violation
        public void seriously() {

        }
    }
}
