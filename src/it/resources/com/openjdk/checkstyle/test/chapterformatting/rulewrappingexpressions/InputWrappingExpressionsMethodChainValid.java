package com.openjdk.checkstyle.test.chapterformatting.rulewrappingexpressions;

import java.util.List;

public class InputWrappingExpressionsMethodChainValid {

    public List<Integer> method(List<Integer> list) {
        return list
                .stream()
                .toList();
    }

    class Inner {
        class Inner2 {
        }
        Inner.
                Inner2 inner = new Inner2();
    }

}
