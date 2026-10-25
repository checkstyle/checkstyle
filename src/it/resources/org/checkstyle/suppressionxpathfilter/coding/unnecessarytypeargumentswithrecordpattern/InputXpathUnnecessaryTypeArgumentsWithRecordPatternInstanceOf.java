/*
UnnecessaryTypeArgumentsWithRecordPattern


*/

package org.checkstyle.suppressionxpathfilter.coding.unnecessarytypeargumentswithrecordpattern;

public class InputXpathUnnecessaryTypeArgumentsWithRecordPatternInstanceOf {

    record Box<T>(T t) {}

    void test(Box<String> box) {
        if (box instanceof Box<String>(var s)) {
        // violation above 'Unnecessary type arguments with record pattern.'
            System.out.println(s);
        }
    }
}
