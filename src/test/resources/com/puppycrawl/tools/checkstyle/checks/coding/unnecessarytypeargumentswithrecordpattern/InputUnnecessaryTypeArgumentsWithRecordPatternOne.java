/*
UnnecessaryTypeArgumentsWithRecordPattern

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarytypeargumentswithrecordpattern;

public class InputUnnecessaryTypeArgumentsWithRecordPatternOne {
    interface IBox<T> {}
    record Box<T>(T t) implements IBox<T> { }

    void test1(Box<Box<String>> bbs) {
        // 2 violations 3 lines below:
        //                        'Unnecessary type arguments with record pattern.'
        //                        'Unnecessary type arguments with record pattern.'
        if (bbs instanceof Box<Box<String>>(Box<String>(var s))) {
            System.out.println("String " + s);
        }
        // violation below 'Unnecessary type arguments with record pattern.'
        if (bbs instanceof Box<Box<String>>(Box(var s))) {
            System.out.println("String " + s);
        }
        if (bbs instanceof Box(Box(var s))) {
            System.out.println("String " + s);
        }
        // Here the compiler will infer that
        // the entire instanceof pattern is Box<Box<String>>(Box<String>(var s)),
    }

    void test3(Box obj) { // raw use
        // if (obj instanceof Box(var s)) { } this will not compile, compiler can't infer type
        // if (obj instanceof Box<String>(var s)) { } this will not compile,
        //      Box' cannot be safely cast to 'Box<String>'
        if (obj instanceof Box<?>(var s)) { // only wildcard is allowed,
                // so we shouldn't violate type arguments if it is a wildcard
            System.out.println("String " + s);
        }
    }

    void test3(IBox<Box<String>> ibs) {
       switch (ibs) {
           // 2 violations 3 lines below:
           //                        'Unnecessary type arguments with record pattern.'
           //                        'Unnecessary type arguments with record pattern.'
           case Box<Box<String>>(Box<String>(var s)) ->
               System.out.println("String " + s);
           default -> {}
       }
       switch (ibs) {
           case Box(Box(var s)) ->
               System.out.println("String " + s);
           default -> {}
       }
    }

}
