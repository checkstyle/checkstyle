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

public class InputEmptyLineSeparatorEnumMembersWithJavadoc {

    enum Operation {


        /**
         * Addition operation with class body.
         */
        ADD(1, "+") {
            // violation 4 lines above ''/\*' has more than 1 empty lines before.'
            @Override
            public int calculate(int a, int b) {
                return a + b;
            }
        },


        // Subtraction operation with class body
        SUB(2, "-") {
            // violation 2 lines above ''//' has more than 1 empty lines before.'
            @Override
            public int calculate(int a, int b) {
                return a - b;
            }
        },

        /*
         * Multiplication operation.
         */
        MUL(3, "*") {
            @Override
            public int calculate(int a, int b) {
                return a * b;
            }
        },

        /**
         * Division operation.
         */
        DIV(4, "/");


        /**
         * Constant field for default code.
         */
        public static final int DEFAULT_CODE = 0;
        // violation 4 lines above ''/\*' has more than 1 empty lines before.'


        // Constant field for default symbol
        public static final String DEFAULT_SYMBOL = "";
        // violation 2 lines above ''//' has more than 1 empty lines before.'


        /**
         * Identifier of the operation.
         */
        private final int code;
        // violation 4 lines above ''/\*' has more than 1 empty lines before.'


        // The math symbol of the operation
        private final String symbol;
        // violation 2 lines above ''//' has more than 1 empty lines before.'


        /**
         * Default constructor.
         */
        Operation() { // violation 3 lines above ''/\*' has more than 1 empty lines before.'
            this.code = DEFAULT_CODE;
            this.symbol = DEFAULT_SYMBOL;
        }


        // Constructor with code and symbol
        Operation(int code, String symbol) {
            // violation 2 lines above ''//' has more than 1 empty lines before.'
            this.code = code;
            this.symbol = symbol;
        }


        /**
         * Calculates the result.
         *
         * @param a first operand
         * @param b second operand
         * @return calculation result
         */
        public int calculate(int a, int b) {
            // violation 8 lines above ''/\*' has more than 1 empty lines before.'
            return 0;
        }


        // Gets operation code
        public int getCode() {
            // violation 2 lines above ''//' has more than 1 empty lines before.'
            return code;
        }
    }
}
