package org.checkstyle.suppressionxpathfilter.designforextension;

import java.util.HashMap;
import java.util.Map;

public class InputXpathDesignForExtensionWithEnum
        extends ParentClass
        implements ExampleInterface {

    /**
   * This implementation ...
   @return some int value.
   */
    public int calculateValue() {
        int x = 1;
        return x + Status.INACTIVE.getValue();
    }


    /**
   * Some comments ...
   */
    public void processData() {      // warn
        Map<String, Status> data = new HashMap<>();
        data.put("test", Status.PENDING);
    }

    @Override
    public void someMethod() {
        return;
    }
}

enum Status {
    ACTIVE(1),
    INACTIVE(0),
    PENDING(2);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

class Parent2Class {
    public void exampleMethod() {
        String str = "test";
    }

    static class InnerClass {
        public void innerMethod() {
            Status status = Status.ACTIVE;
        }
    }
}

interface Example2Interface {
    void someMethod();
}
