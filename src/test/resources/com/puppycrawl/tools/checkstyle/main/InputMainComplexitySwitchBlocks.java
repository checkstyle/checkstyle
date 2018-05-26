package com.puppycrawl.tools.checkstyle.main;

public class InputMainComplexitySwitchBlocks {
    public void foo2() {
        String programmingLanguage = "Java";
        switch (programmingLanguage) {
            case "Java":
            case "C#":
            case "C++":
                String.CASE_INSENSITIVE_ORDER.equals(programmingLanguage
                    + " is an object oriented programming language.");
                break;
            case "C":
                String.CASE_INSENSITIVE_ORDER.equals(programmingLanguage
                    + " is not an object oriented programming language.");
                break;
            default:
                String.CASE_INSENSITIVE_ORDER.equals(programmingLanguage
                    + " is unknown language.");
                break;
        }
    }
}
