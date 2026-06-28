package com.openjdk.checkstyle.test.chapterformatting.rulewrappingexpressions;

import java.util.List;

public class InputWrappingExpressionsMethodChainInvalid {

    public List<Integer> method(List<Integer> list) {
        // violation below ''.' should be on a new line'
        return list.
                stream().
                toList();
        // violation 2 lines above ''.' should be on a new line'
    }

    public void additionalViolations(List<String> strings) {
        // violation below ''.' should be on a new line'
        strings.
                stream().
                // violation above ''.' should be on a new line'
                // violation below ''.' should be on a new line'
                filter(s -> !s.isEmpty()).
                map(String::toLowerCase).
                forEach(System.out::println);
        // violation 2 lines above ''.' should be on a new line'

        // violation 2 lines below ''.' should be on a new line'
        strings.stream()
                  .filter(s -> s.length() > 3).
                  map(s -> s + "!")
                  .toList();

        // violation below ''.' should be on a new line'
        String result = Optional.ofNullable("test").
                orElse("default").
                toUpperCase();
        // violation 2 lines above ''.' should be on a new line'
    }

    public void method3(StringBuilder str) {
        // violation below ''.' should be on a new line'
        if (str.
                toString().
                        equals("hello")) {
            // violation 2 lines above ''.' should be on a new line'
            System.out.println("hello");
        }
    }

    class Inner {
        class Inner2 {
        }
        Inner.
                Inner2 inner = new Inner2();
        Inner.
                Inner2 inner2 = new Inner().
                                new Inner2();
    }

    public void myMethod() {
        java.lang.
                String text = java.
                lang.
                String.CASE_INSENSITIVE_ORDER.
                toString();
                // violation 2 lines above ''.' should be on a new line'
    }
}
