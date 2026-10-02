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

public class InputEmptyLineSeparatorEnumWithComments {

    enum State {
        // New state
        NEW,
        // Successful state
        SUCCESS,
        // Failed state
        FAILURE
    }

    enum State1 {


        // New state

        NEW, // violation 2 lines above ''//' has more than 1 empty lines before.'


        // Successful state

        SUCCESS, // violation 2 lines above ''//' has more than 1 empty lines before.'


        // Failed state

        FAILURE // violation 2 lines above ''//' has more than 1 empty lines before.'
    }

    enum State2 {


        // New state
        NEW, // violation above ''//' has more than 1 empty lines before.'




        // Successful state
        SUCCESS, // violation above ''//' has more than 1 empty lines before.'


        // Failed state
        FAILURE // violation above ''//' has more than 1 empty lines before.'
    }


    enum State3 { // violation ''ENUM_DEF' has more than 1 empty lines before.'


        /*
         * This represents a newly created state.
         */
        NEW, // violation 3 lines above ''/\*' has more than 1 empty lines before.'


        /*
         * This represents a successful state.
         */
        SUCCESS, // violation 3 lines above ''/\*' has more than 1 empty lines before.'


        /*
         * This represents a failed state.
         */
        FAILURE // violation 3 lines above ''/\*' has more than 1 empty lines before.'
    }

    enum State4 {


        /* New state */
        NEW, // violation above ''/\*' has more than 1 empty lines before.'
        /* Successful state */
        SUCCESS,
        /* Failed state */
        FAILURE
    }

    enum State5 {


        // State transition
        // happens after validation
        SUCCESS { // violation 2 lines above ''//' has more than 1 empty lines before.'
            @Override
            public String getMessage() {
                return "Success";
            }
        },


        // State transition
        // happens after failure
        FAILURE; // violation 2 lines above ''//' has more than 1 empty lines before.'

        public String getMessage() {
            return "Failure";
        }
    }
}
