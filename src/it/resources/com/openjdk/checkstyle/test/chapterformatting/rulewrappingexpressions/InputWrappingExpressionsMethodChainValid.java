package com.openjdk.checkstyle.test.chapterformatting.rulewrappingexpressions;

import java.util.List;

public class InputWrappingExpressionsMethodChainValid {

    public List<Integer> method(List<Integer> list) {
        return list
                .stream()
                .toList();
    }

    public List<Integer> method2(List<Integer> list) {
        return list.stream().toList();
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
                String.CASE_INSENSITIVE_ORDER
                .toString();
    }
}
