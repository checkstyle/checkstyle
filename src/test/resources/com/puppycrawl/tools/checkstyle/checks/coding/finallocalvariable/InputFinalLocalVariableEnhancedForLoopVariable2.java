/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = VARIABLE_DEF, PARAMETER_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableEnhancedForLoopVariable2 {
    public void method1()
    {
        final java.util.List<Object> list = new java.util.ArrayList<>();

        for(Object a : list){
        }
    }

    public void method2()
    {
        final int[] squares = {0, 1, 4, 9, 16, 25};
        int x; // violation, "Variable 'x' should be declared final"
        for (final int i : squares) {
        }

    }
    // violation below "Variable 'snippets' should be declared final"
    public java.util.List<String> method3(java.util.List<String> snippets) {
        // violation below "Variable 'filteredSnippets' should be declared final"
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

        Object a; // violation, "Variable 'a' should be declared final"
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
