/*
CyclomaticComplexity
max = 0
switchBlockAsSingleDecisionPoint = (default)false
tokens = (default)CTOR_DEF, METHOD_DEF, INSTANCE_INIT, STATIC_INIT, LITERAL_WHILE, LITERAL_DO, \
         LITERAL_FOR, LITERAL_IF, LITERAL_SWITCH, LITERAL_CASE, LITERAL_CATCH, QUESTION, \
         LAND, LOR, COMPACT_CTOR_DEF, LITERAL_WHEN


*/

package org.checkstyle.suppressionxpathfilter.metrics.cyclomaticcomplexity;

public class InputXpathCyclomaticComplexitySwitchBlock {


    public void foo2() { // violation 'Cyclomatic Complexity is 5 (max allowed is 0).'
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
