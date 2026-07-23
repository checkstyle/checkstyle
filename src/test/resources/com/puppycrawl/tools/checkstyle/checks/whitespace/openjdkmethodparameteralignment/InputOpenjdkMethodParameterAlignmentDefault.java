/*
OpenjdkMethodParameterAlignment
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.openjdkmethodparameteralignment;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class InputOpenjdkMethodParameterAlignmentDefault {

    void noParameters() {
    }

    void singleParameter(int a) {
    }

    void singleLine(int a, int b, int c) {
    }

    void listedVertically(int a,
                          int b,
                          int c) {
    }

    void twoOnTheLastLine(int a,
                          int b, int c) {
        // violation above 'Only one parameter is allowed per line in a vertical list.'
    }

    void twoOnTheFirstLine(int a, int b,
                           int c) {
        // violation 2 lines above 'Only one parameter is allowed per line in a vertical list.'
    }

    void threeOnOneLine(int a,
                        int b, int c, int d,
                        int e) {
        // violation 2 lines above 'Only one parameter is allowed per line in a vertical list.'
        // violation 3 lines above 'Only one parameter is allowed per line in a vertical list.'
    }

    int wrappedByEightSpaces(String aString, List<Integer> aList,
            Map<String, String> aMap, int anInt, long aLong,
            double aDouble, long anotherLong) {
        return 0;
    }

    int brokenAfterOpeningParenthesis(
            String aString, List<Integer> aList,
            Map<String, String> aMap) {
        return 0;
    }

    int notAlignedWithFirstParameter(String aString,
        List<Integer> aList, Map<String, String> aMap) {
        return 0;
    }

    int listedVerticallyWithThrows(String aString,
                                   List<Integer> aList,
                                   Map<String, String> aMap)
            throws IllegalArgumentException {
        return 0;
    }

    int twoOnOneLineWithThrows(String aString,
                               List<Integer> aList, Set<Number> aSet)
            throws IllegalArgumentException {
        // violation 2 lines above 'Only one parameter is allowed per line in a vertical list.'
        return 0;
    }
}
