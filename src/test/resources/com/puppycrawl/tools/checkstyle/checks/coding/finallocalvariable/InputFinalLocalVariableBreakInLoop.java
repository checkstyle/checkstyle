/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

import java.util.List;

public class InputFinalLocalVariableBreakInLoop {

    void whileLoop() {
        int a; // violation "Variable 'a' should be declared final"
        final int random = (int) Math.random();
        while (random > 0) {
            if (random > 0) {
                a = 1;
                break;
            }
        }
    }

    void forLoop(List<String> list) {
        String first; // violation "Variable 'first' should be declared final"
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isEmpty()) {
                first = list.get(i);
                break;
            }
        }
    }

    void forEachLoop(List<String> list) {
        String first; // violation "Variable 'first' should be declared final"
        for (String element : list) {
            first = element;
            break;
        }
    }

    void doWhileLoop(boolean flag) {
        int a; // violation "Variable 'a' should be declared final"
        do {
            if (flag) {
                a = 1;
                break;
            }
        } while (flag);
    }

    void statementsBeforeBreak(List<String> list) {
        String first; // violation "Variable 'first' should be declared final"
        for (String element : list) {
            if (!element.isEmpty()) {
                first = element;
                System.out.println(first);
                break;
            }
        }
    }

    void declaredInOuterLoop(List<List<String>> lists) {
        for (List<String> list : lists) {
            String first; // violation "Variable 'first' should be declared final"
            for (String element : list) {
                if (!element.isEmpty()) {
                    first = element;
                    break;
                }
            }
        }
    }

    void variableDefinitionBeforeBreak(List<String> list) {
        String first; // violation "Variable 'first' should be declared final"
        for (String element : list) {
            if (!element.isEmpty()) {
                first = element;
                final int length = first.length();
                System.out.println(length);
                break;
            }
        }
    }
}
