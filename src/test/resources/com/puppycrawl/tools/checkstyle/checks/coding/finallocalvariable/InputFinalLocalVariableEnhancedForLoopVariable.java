package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableEnhancedForLoopVariable {
    public void method1()
    {
        final java.util.List<Object> list = new java.util.ArrayList<>();

        for(Object a : list){
        }
    }

    public void method2()
    {
        final int[] squares = {0, 1, 4, 9, 16, 25};
        int x;
        for (final int i : squares) {
        }

    }

    public java.util.List<String> method3(java.util.List<String> snippets) {
        java.util.List<String> filteredSnippets = new java.util.ArrayList<>();
        for (String snippet : snippets) {
            filteredSnippets.add(snippet);
        }
        if (filteredSnippets.size() == 0) {
            String snippet = snippets.get(0);
            snippet = new String(snippet);
            filteredSnippets.add(snippet);
        }
        return filteredSnippets;
    }

    public void method4()
    {
        final java.util.List<Object> list = new java.util.ArrayList<>();

        for(Object a : list) {
        }

        Object a;
        if (list.isEmpty())
        {
            a = new String("empty");
        }
        else
        {
            a = new String("not empty");
        }

        for(Object b : list) {
            b = new String("b");
        }
    }

}
