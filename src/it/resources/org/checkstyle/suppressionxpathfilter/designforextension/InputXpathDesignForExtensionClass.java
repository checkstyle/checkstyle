package org.checkstyle.suppressionxpathfilter.designforextension;

public class InputXpathDesignForExtensionClass
        extends ParentClass
        implements ExampleInterface {

    public int calculateValue() {  //warn
        int x = 1;
        return x + 5;
    }

    @Override
    public void someMethod() {
        return;
    }
}

class ParentClass {
    public void exampleMethod() {
        String str = "test";
    }

    static class InnerClass {
        public void innerMethod() {
            int y = 10;
        }
    }
}

interface ExampleInterface {
    void someMethod();
}
