package org.checkstyle.suppressionxpathfilter.design.designforextension;

public class InputXpathDesignForExtensionProtected
        extends ParentClass
        implements ExampleInterface {

    protected int getValue() {  // warn
        return 1;
    }

    @Override
    public void someMethod() {
        return;
    }
}
