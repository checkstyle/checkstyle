/*
DuplicateSwitchBranch


*/

package com.puppycrawl.tools.checkstyle.checks.coding.duplicateswitchbranch;

public class InputDuplicateSwitchBranch {

    int testStatement(String value) {
        switch (value) {
            case "A":
                return 1;
            case "B": // violation 'Duplicate\ switch\ branch\ implementation\ identical\ to\ the\ branch\ at\ line\ 13.'
                return 1;
            case "C":
                return 2;
            default:
                return 0;
        }
    }

    int testExpression(String value) {
        return switch (value) {
            case "A" -> 1;
            case "B" -> 1; // violation 'Duplicate\ switch\ branch\ implementation\ identical\ to\ the\ branch\ at\ line\ 26.'
            case "C" -> 2;
            default -> 0;
        };
    }

    void testMultipleLabels(String value) {
        switch (value) {
            case "A":
            case "B":
                System.out.println("Hello");
                break;
            case "C":
                System.out.println("World");
                break;
            default:
                break;
        }
    }

    void testDifferentImplementations(int value) {
        switch (value) {
            case 1:
                System.out.println(1);
                break;
            case 2:
                System.out.println(2);
                break;
            default:
                break;
        }
    }

    int testBlockRules(String value) {
        return switch (value) {
            case "A" -> {
                int x = 1;
                yield x;
            }
            case "B" -> { // violation 'Duplicate\ switch\ branch\ implementation\ identical\ to\ the\ branch\ at\ line\ 62.'
                int x = 1;
                yield x;
            }
            default -> 0;
        };
    }
}
