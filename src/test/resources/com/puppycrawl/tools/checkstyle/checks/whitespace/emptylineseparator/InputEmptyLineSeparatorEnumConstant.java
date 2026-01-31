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

public class InputEmptyLineSeparatorEnumConstant {

    public enum Check {


        FIRST, // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'
        SECOND,


        THIRD, // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'



        FOURTH, // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'



        SPECIAL(100) { // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'
            @Override
            public String describe() {
                return "Special enum constant";
            }
        },



        NORMAL(200) { // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'
            @Override
            public String describe() {
                return "Normal enum constant";
            }
        };



        private final int code; // violation ''VARIABLE_DEF' has more than 1 empty lines before.'

        Check(int code) {
            this.code = code;
        }

        public abstract String describe();
    }
}
