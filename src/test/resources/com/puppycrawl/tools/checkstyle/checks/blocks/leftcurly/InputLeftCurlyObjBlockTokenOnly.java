/*
LeftCurly
option = (default)eol
ignoreEnums = false
tokens = OBJBLOCK


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyObjBlockTokenOnly {
    enum Colors {RED,
        BLUE,
        GREEN;
    }

    enum Shapes {
        SQUARE,
        CIRCLE;
    }

    // violation below ''{' at column 41 should have line break after'
    Runnable anonymous = new Runnable() { @Override
        public void run() {
        }
    };
}
