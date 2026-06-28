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

    class Inner {
        class Inner2 {
        }
        Inner.
                Inner2 inner = new Inner2();
    }

}
