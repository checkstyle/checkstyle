package org.checkstyle.suppressionxpathfilter.coding.unnecessarytypeargumentswithrecordpattern;

public class InputXpathUnnecessaryTypeArgumentsWithRecordPatternCaseGroup {

    record Box<T>(T t) {}

    void test(Box<String> box) {
        switch (box) {
            case Box<String>(var s): //warn
                System.out.println(s);
                break;
            default:
                break;
        }
    }
}
