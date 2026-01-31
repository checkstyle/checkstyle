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

public class InputEmptyLineSeparatorEnum {


    private enum State {NEW, SUCCESS, FAILURE}
    // violation above ''ENUM_DEF' has more than 1 empty lines before.'



    private enum State1 {
        // violation above ''ENUM_DEF' has more than 1 empty lines before.'


        NEW, SUCCESS, FAILURE,
        // 2 violations above:
        // ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'
        // ''ENUM_CONSTANT_DEF' has more than 1 empty lines after.'



    }

    private enum State2 {


        NEW, SUCCESS, // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'


        FAILURE, // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines after.'
        // violation above ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'



    }

    private enum State3 {


        /**
         * For testing.
         */
        NEW, // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'

        /**
         * For testing.
         */
        SUCCESS,

        FAILURE
        // single line comment

        // testing comment 1

        // testing comment 2

        // testing comment 3

    }

    private enum State4 {
        NEW,
        SUCCESS,
        FAILURE, // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines after.'
        // testing comment
        // testing comment

        // testing comment


        // testing comment
        // testing comment
    }

    private enum State5
    {


        NEW, // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'


            SUCCESS, // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'


                    FAILURE // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'
    }

    private enum State6
    {
        NEW }

    private enum State7
    { NEW }
}
