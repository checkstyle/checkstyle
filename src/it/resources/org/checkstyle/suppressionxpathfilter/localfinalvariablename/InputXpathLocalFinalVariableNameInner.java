package org.checkstyle.suppressionxpathfilter.localfinalvariablename;

import java.util.Scanner;

public class InputXpathLocalFinalVariableNameInner {
    class InnerClass {
        void MyMethod() {
            final int VAR1 = 10; // warn
        }
    }
}
