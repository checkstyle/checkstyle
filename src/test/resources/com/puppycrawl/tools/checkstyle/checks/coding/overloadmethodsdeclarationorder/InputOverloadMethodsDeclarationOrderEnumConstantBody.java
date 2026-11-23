/*
OverloadMethodsDeclarationOrder
orderByIncreasingParameterCount = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

enum InputOverloadMethodsDeclarationOrderEnumConstantBody {
    FIRST {
        void overload(int i) {
        }

        void other() {
        }

        // violation below 'All overloaded methods should be placed next to each other.'
        void overload(String s) {
        }
    },
    SECOND {
        void overload(int i) {
        }

        void overload(String s) {
        }

        void other() {
        }
    },
    THIRD
}
