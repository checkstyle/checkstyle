package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyIgnoreEnums {
    enum Colors {RED,
        BLUE,
        GREEN;

        @Override public String toString() { return ""; };
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
