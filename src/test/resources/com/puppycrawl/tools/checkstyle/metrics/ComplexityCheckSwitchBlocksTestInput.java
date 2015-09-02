package com.puppycrawl.tools.checkstyle.metrics;

public class ComplexityCheckSwitchBlocksTestInput {
    public void foo2() {
        String programmingLanguage = "Java";
        switch (programmingLanguage) {
            case "Java":
            case "C#":
            case "C++":
                System.out.printf(programmingLanguage + " is an object oriented programming language.");
                break;
            case "C":
                System.out.printf(programmingLanguage + " is not an object oriented programming language.");
                break;
            default:
                System.out.printf(programmingLanguage + " is unknown language.");
                break;
        }
    }
}
