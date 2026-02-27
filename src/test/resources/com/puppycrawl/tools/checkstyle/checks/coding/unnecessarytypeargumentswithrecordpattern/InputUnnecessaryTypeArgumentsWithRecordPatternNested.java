/*
UnnecessaryTypeArgumentsWithRecordPattern

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarytypeargumentswithrecordpattern;

public class InputUnnecessaryTypeArgumentsWithRecordPatternNested {

    record Box<T>(T t) {}

    void testInstanceof(Box<Box<String>> box) {

        // 2 violations 3 lines below:
        //                        'Unnecessary type arguments with record pattern.'
        //                        'Unnecessary type arguments with record pattern.'
        if (box instanceof Box<Box<String>>(Box<String>(var s))) {
            System.out.println(s);
        }
        // violation below 'Unnecessary type arguments with record pattern.'
        if (box instanceof Box<Box<?>> (Box<?> (var s))) {
            System.out.println(s);
        }

        if (box instanceof Box(Box(var s))) {
            System.out.println(s);
        }
    }

    void testSwitch(Box<Box<String>> box) {
        switch (box) {
            // 2 violations 3 lines below:
            //                        'Unnecessary type arguments with record pattern.'
            //                        'Unnecessary type arguments with record pattern.'
            case Box<Box<String>>(Box<String>(var s)) ->
                System.out.println(s);
            case Box(Box(var s)) ->
                System.out.println(s);
            default -> {}
        }
    }
}
