package org.checkstyle.suppressionxpathfilter.naming.localfinalvariablename;

import java.util.Scanner;

public class InputXpathLocalFinalVariableNameInner {
    class InnerClass {
        void MyMethod() {
            final int VAR1 = 10; // warn
        }
    }
}
