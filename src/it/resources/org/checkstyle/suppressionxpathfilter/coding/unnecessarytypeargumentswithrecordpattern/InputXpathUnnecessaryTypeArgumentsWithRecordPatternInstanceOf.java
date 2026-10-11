package org.checkstyle.suppressionxpathfilter.coding.unnecessarytypeargumentswithrecordpattern;

public class InputXpathUnnecessaryTypeArgumentsWithRecordPatternInstanceOf {

    record Box<T>(T t) {}

    void test(Box<String> box) {
        if (box instanceof Box<String>(var s)) { // warn
            System.out.println(s);
        }
    }
}
