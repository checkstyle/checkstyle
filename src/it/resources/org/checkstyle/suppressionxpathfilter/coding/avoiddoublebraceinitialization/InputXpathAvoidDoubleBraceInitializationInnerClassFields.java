package org.checkstyle.suppressionxpathfilter.coding.avoiddoublebraceinitialization;

public class InputXpathAvoidDoubleBraceInitializationInnerClassFields {
    public int x;
    public int y;

    class InnerClass {
        public void doInitialize() {
            new InputXpathAvoidDoubleBraceInitializationInnerClassFields() {{ /** warn */
                this.x = x + 1;
                this.y = y + 1;
            }};
        }
    }
}
