package org.checkstyle.suppressionxpathfilter.coding.declarationorder;

public class InputXpathDeclarationOrderInnerClass {
    class InnerClass {
        private int b;
        public int a; // warn
    }
}
