/*
DuplicateSwitchBranch


*/

package com.puppycrawl.tools.checkstyle.checks.coding.duplicateswitchbranch;

public class InputDuplicateSwitchBranchSkippedBranches {

    void testLastGroupWithoutBreak(int value) {
        switch (value) {
            case 1:
                System.out.println(1);
                break;
            default:
                System.out.println(2);
        }
    }

    void testOldStylePatterns(Object o) {
        switch (o) {
            case Integer x:
                System.out.println(x);
                break;
            case String x:
                System.out.println(x);
                break;
            default:
                break;
        }
    }

    record Point(int x, int y) { }

    void testRecordAndGuardedPatterns(Object o) {
        switch (o) {
            case Point(int x, int y) -> System.out.println(x);
            case String s when s.isEmpty() -> System.out.println(s);
            case String s -> System.out.println(s);
            default -> { }
        }
    }

    void testDifferentBodyShapes(int value) {
        switch (value) {
            case 1 -> System.out.println(value);
            case 2 -> {
                System.out.println(value);
            }
            default -> { }
        }
    }

    void call(int... args) { }

    void testDifferentArgumentCounts(int value) {
        switch (value) {
            case 1:
                call(1);
                break;
            case 2:
                call();
                break;
            case 3:
                call(1, 2);
                break;
            default:
                break;
        }
    }
}
