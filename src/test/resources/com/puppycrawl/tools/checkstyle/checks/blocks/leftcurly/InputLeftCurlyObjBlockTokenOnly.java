/*
LeftCurly
option = (default)eol
ignoreEnums = false
tokens = OBJBLOCK


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyObjBlockTokenOnly {
    enum Colors {RED, // violation ''{' at column 17 should have line break after'
        BLUE,
        GREEN;
    }

    enum Shapes {
        SQUARE,
        CIRCLE;
    }

    Runnable anonymous = new Runnable() {
        @Override
        public void run() {
        }
    };
}
