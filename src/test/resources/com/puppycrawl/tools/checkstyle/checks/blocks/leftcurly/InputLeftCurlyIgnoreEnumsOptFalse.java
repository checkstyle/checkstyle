package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/*
 * Config:
 * option = eol
 * ignoreEnums = false
 */
public class InputLeftCurlyIgnoreEnumsOptFalse {
    enum Colors {RED, // violation
        BLUE,
        GREEN;

        @Override public String toString() { return ""; }; // violation
    }

    enum Languages {
        JAVA,
        PHP,
        SCALA,
        C,
        PASCAL
    }

    void method1(int a) {
        switch (a) {case 1: ; }
    }
}
