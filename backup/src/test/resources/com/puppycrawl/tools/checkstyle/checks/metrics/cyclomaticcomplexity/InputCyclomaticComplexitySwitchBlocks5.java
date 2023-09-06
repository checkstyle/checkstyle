/*
CyclomaticComplexity
max = (default)10
switchBlockAsSingleDecisionPoint = (default)false
tokens = (default)LITERAL_WHILE, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_SWITCH, \
         LITERAL_CASE, LITERAL_CATCH, QUESTION, LAND, LOR


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.cyclomaticcomplexity;

public class InputCyclomaticComplexitySwitchBlocks5 {
    public void foo2() { // violation 'Cyclomatic Complexity is 12 (max allowed is 10)'
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
            case "D":
                String.CASE_INSENSITIVE_ORDER.equals(programmingLanguage +
                    " is not an object oriented programming language.");
                break;
            case "E":
                String.CASE_INSENSITIVE_ORDER.equals(programmingLanguage +
                    " is not an object oriented programming language.");
                break;
            case "F":
                String.CASE_INSENSITIVE_ORDER.equals(programmingLanguage +
                    " is not an object oriented programming language.");
                break;
            case "G":
                String.CASE_INSENSITIVE_ORDER.equals(programmingLanguage +
                    " is not an object oriented programming language.");
                break;
            case "H":
                String.CASE_INSENSITIVE_ORDER.equals(programmingLanguage +
                    " is not an object oriented programming language.");
                break;
            case "I":
                String.CASE_INSENSITIVE_ORDER.equals(programmingLanguage +
                    " is not an object oriented programming language.");
                break;
            case "J":
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
