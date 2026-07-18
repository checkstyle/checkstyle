/*
SuppressWarnings
format = (default)^\\s*+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/
// non-compiled with javac: Compilable with Java21 individually
// non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
import java.lang.annotation.Documented;

@SuppressWarnings({"unchecked", "unused"})
public class InputSuppressWarningsCompactNonConstant1
{
    // violation below 'The warning '   ' cannot be suppressed at this location'
    @SuppressWarnings({"   "})
    class Empty {

        // violation below 'The warning '' cannot be suppressed at this location'
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
            Object o = new InputSuppressWarningsCompactNonConstant1() {

                @Override
                @SuppressWarnings({"unchecked"})
                public String toString() {
                    return "";
                }
            };
        }
    }

    @SuppressWarnings({"invalid"})
    @Documented
    @interface Sweet {
        int cool();
    }

    @Documented
    @SuppressWarnings({})
    // violation above 'The warning '' cannot be suppressed at this location'
    @interface MoreSweetness {

        @SuppressWarnings({"unused", "ignore"})
        int cool();
    }

    public class Junk {

        // violation below 'The warning '' cannot be suppressed at this location'
        @SuppressWarnings({})
        int a = 1;

        @SuppressWarnings({"unchecked"})
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings({"unchecked"})String y) {}
    }
    // 2 violations 3 lines below:
    // 'cannot be suppressed at this location'
    // 'cannot be suppressed at this location'
    @SuppressWarnings({(false) ? "unchecked" : "", (false) ? "unchecked" : ""})
    class Cond {

        // violation below 'The warning '' cannot be suppressed at this location'
        @SuppressWarnings({(false) ? "" : "unchecked"})
        public Cond() {

        }

        // violation below 'The warning '   ' cannot be suppressed at this location'
        @SuppressWarnings({(false) ? (true) ? "   " : "unused" : "unchecked",
            (false) ? (true) ? "   " : "unused" : "unchecked"})
        // violation above 'The warning '   ' cannot be suppressed at this location'
        public void aCond1() {}

        // violation below 'The warning '   ' cannot be suppressed at this location'
        @SuppressWarnings({(false) ? "unchecked" : (true) ? "   " : "unused"})
        public void aCond2() {}

        // 2 violations 4 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @java.lang.SuppressWarnings({(false) ? "unchecked" :
                ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused",
                // violation below 'The warning '' cannot be suppressed at this location'
            (false) ? "unchecked" : ("" == "") ? (false) ? (true) ? "" :
                    // violation below 'The warning '   ' cannot be suppressed at this location'
                    "foo" : "   " :
                    "unused"})
        public void seriously() {

        }
    }
}
