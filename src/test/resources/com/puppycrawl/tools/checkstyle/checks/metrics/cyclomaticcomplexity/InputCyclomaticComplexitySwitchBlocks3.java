/*
CyclomaticComplexity
max = 5
switchBlockAsSingleDecisionPoint = (default)false
tokens = (default)LITERAL_WHILE, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_SWITCH, \
         LITERAL_CASE, LITERAL_CATCH, QUESTION, LAND, LOR


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.cyclomaticcomplexity;

public class InputCyclomaticComplexitySwitchBlocks3 {
    public void foo2() {
        String programmingLanguage = "Java";
        switch (programmingLanguage) {
            case "Java":
            case "C#":
            case "C++":
                String.CASE_INSENSITIVE_ORDER.equals(programmingLanguage +
                    " is an object oriented programming language.");
                break;
            case "C":
                String.CASE_INSENSITIVE_ORDER.equals(programmingLanguage +
                    " is not an object oriented programming language.");
                break;
            default:
                String.CASE_INSENSITIVE_ORDER.equals(programmingLanguage +
                    " is unknown language.");
                break;
        }
    }
}
