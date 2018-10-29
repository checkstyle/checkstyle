package org.checkstyle.suppressionxpathfilter.cyclomaticcomplexity;

public class SuppressionXpathRegressionCyclomaticTwo {


    public void foo2() { //warn
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
