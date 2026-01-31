/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF, ENUM_CONSTANT_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorEnumConstantDefault {

    public enum Check {


        FIRST,
        SECOND,


        THIRD,



        FOURTH,



        SPECIAL(100) {
            @Override
            public String describe() {
                return "Special enum constant";
            }
        },


        NORMAL(200) {
            @Override
            public String describe() {
                return "Normal enum constant";
            }
        };



        private final int code;

        public Check(int code) {
            this.code = code;
        }

        public abstract String describe();
    }
}
