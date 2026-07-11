package com.openjdk.checkstyle.test.chapterformatting.rulewrappingexpressions;

// violation first line 'Header mismatch'

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

    public void multiLineStrings() {
        String[] rules = """
                Welcome, %s!
                Rule 1: Always write clean code.
                Rule 2: Don't forget your semicolons.
                Rule 3: Keep it simple.
                """
                .formatted("user")
                .lines()
                .filter(line -> line.contains("Rule"))
                .toArray(String[]::new);

        String rules2 = """
                Welcome, %s!
                Rule 1: Always write clean code.
                Rule 2: Don't forget your semicolons.
                Rule 3: Keep it simple.
                """.formatted("user");
    }
}
