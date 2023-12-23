/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough16 {

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
        switch (a) {case 1:;}
        // violation above 'Fall\ through from the last branch of the switch statement.'
    }
}
