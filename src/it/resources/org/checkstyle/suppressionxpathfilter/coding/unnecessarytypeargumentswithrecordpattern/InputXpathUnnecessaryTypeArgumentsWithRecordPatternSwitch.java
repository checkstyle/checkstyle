/*
UnnecessaryTypeArgumentsWithRecordPattern


*/

package org.checkstyle.suppressionxpathfilter.coding.unnecessarytypeargumentswithrecordpattern;

public class InputXpathUnnecessaryTypeArgumentsWithRecordPatternSwitch {

    record Box<T>(T t) {}

    void test(Box<String> box) {
        switch (box) {
            case Box<String>(var s) ->
            // violation above 'Unnecessary type arguments with record pattern.'
                System.out.println(s);
            default -> {}
        }
    }
}
