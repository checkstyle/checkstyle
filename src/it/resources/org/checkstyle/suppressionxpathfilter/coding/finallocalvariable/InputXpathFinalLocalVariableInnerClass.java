package org.checkstyle.suppressionxpathfilter.coding.finallocalvariable;

public class InputXpathFinalLocalVariableInnerClass {
    class InnerClass {
        public void test1() {
            final boolean b = true;
            int shouldBeFinal; // warn

            if (b) {
                shouldBeFinal = 1;
            } else {
                shouldBeFinal = 2;
            }
        }

    }
}
