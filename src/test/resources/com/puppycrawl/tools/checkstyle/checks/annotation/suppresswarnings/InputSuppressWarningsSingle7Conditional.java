/*
SuppressWarnings
format = ^unchecked$*|^unused$*|.*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF, PATTERN_VARIABLE_DEF, MODULE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

public class InputSuppressWarningsSingle7Conditional
{
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
