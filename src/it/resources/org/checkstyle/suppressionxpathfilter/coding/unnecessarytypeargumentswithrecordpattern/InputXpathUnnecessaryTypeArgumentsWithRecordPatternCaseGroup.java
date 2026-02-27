package org.checkstyle.suppressionxpathfilter.coding.unnecessarytypeargumentswithrecordpattern;

public class InputXpathUnnecessaryTypeArgumentsWithRecordPatternCaseGroup {

    record Box<T>(T t) {}

    void test(Object obj) {
        switch (obj) {
            case Box<String>(var s): //warn
                System.out.println(s);
                break;
            default:
                break;
        }
    }
}
