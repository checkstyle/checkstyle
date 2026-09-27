/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF, ENUM_CONSTANT_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorEnumWithJavadoc {

    enum State1 {


        /**
         * Represents a newly created state.
         */
        NEW, // violation 3 lines above ''/\*' has more than 1 empty lines before.'


        /**
         * Represents a successful state.
         */
        SUCCESS, // violation 3 lines above ''/\*' has more than 1 empty lines before.'


        /**
         * Represents a failed state.
         */
        FAILURE // violation 3 lines above ''/\*' has more than 1 empty lines before.'
        // violation above ''ENUM_CONSTANT_DEF' has more than 1 empty lines after.'


    }

    enum State2 {


        /**
         * Represents a successful state.
         */
        SUCCESS { // violation 3 lines above ''/\*' has more than 1 empty lines before.'
            @Override
            public String getMessage() {
                return "Success";
            }
        },


        /**
         * Represents a failed state.
         */
        FAILURE; // violation 3 lines above ''/\*' has more than 1 empty lines before.'


        public String getMessage() {
            // violation above ''METHOD_DEF' has more than 1 empty lines before.'
            return "Failure";
        }
    }

    enum State3 {


        // State transition
        /**
         * Represents a newly created state.
         */
        NEW, // violation 4 lines above ''//' has more than 1 empty lines before.'


        /*
         * Successful transition
         */
        /**
         * Represents a successful state.
         */
        SUCCESS, // violation 6 lines above ''/\*' has more than 1 empty lines before.'


        // Failed transition
        /**
         * Represents a failed state.
         */
        FAILURE // violation 4 lines above ''//' has more than 1 empty lines before.'
    }

    enum State5 {

        // State transition
        // happens after initialization
        /**
         * Represents a newly created state.
         */
        NEW,


        // State transition
        // happens after validation
        /**
         * Represents a successful state.
         */
        SUCCESS, // violation 5 lines above ''//' has more than 1 empty lines before.'

        // State transition
        // happens after failure
        /**
         * Represents a failed state.
         */
        FAILURE // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines after.'


    }
}
