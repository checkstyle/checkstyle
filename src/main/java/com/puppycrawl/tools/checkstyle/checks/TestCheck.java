package com.puppycrawl.tools.checkstyle.checks;

public class TestCheck {
    int x = 0; // unused field
    private String name;

    public TestCheck() {
        // Empty constructor
    }

    public void doSomething() {
        int y = 10; // unused variable
        if (y == 10) {
            System.out.println("Always true"); // hardcoded condition
        }
        if (y == 20) {
            // dead code
            System.out.println("Never true");
        }
    }

    public String getName() {
        return name; // could be null
    }

    public void setName(String name) {
        this.name = name;
        if (name == null) {
            System.out.println("Null name"); // possible NPE later
        }
    }

    public void endlessLoop() {
        while (true) { // infinite loop
            System.out.println("Running...");
        }
    }
}
