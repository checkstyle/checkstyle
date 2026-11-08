/*
SuppressWarnings
format = .*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF, PATTERN_VARIABLE_DEF, MODULE_DEF


*/
// non-compiled with javac: Compilable with Java21 individually
// non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

public class InputSuppressWarningsCompactNonConstant2Conditional
{
    // 4 violations 5 lines below:
    // 'cannot be suppressed at this location'
    // 'cannot be suppressed at this location'
    // 'cannot be suppressed at this location'
    // 'cannot be suppressed at this location'
    @SuppressWarnings({(false) ? "unchecked" : "", (false) ? "unchecked" : ""})
    class Cond {
        // 2 violations 3 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings({(false) ? "" : "unchecked"})
        public Cond() {

        }
        // 3 violations 4 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings({(false) ? (true) ? "   " : "unused" : "unchecked",
            (false) ? (true) ? "   " : "unused" : "unchecked"})
        public void aCond1() {
            // 3 violations 2 lines above:
            // 'cannot be suppressed at this location'
            // 'cannot be suppressed at this location'
            // 'cannot be suppressed at this location'
        }
        // 3 violations 4 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        @SuppressWarnings({(false) ? "unchecked" : (true) ? "   " : "unused"})
        public void aCond2() {

        }
        // violation 13 lines below 'The warning 'unchecked' cannot be suppressed at this location'
        // 4 violations 13 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 2 violations 9 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // 2 violations 7 lines below:
        // 'cannot be suppressed at this location'
        // 'cannot be suppressed at this location'
        // violation 5 lines below 'The warning 'unused' cannot be suppressed at this location'
        @java.lang.SuppressWarnings({(false) ? "unchecked" :
                ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused",
            (false) ? "unchecked" : ("" == "") ? (false) ? (true) ? "" :
                    "foo" : "   " :
                    "unused"})
        public void seriously() {

        }
    }
}
