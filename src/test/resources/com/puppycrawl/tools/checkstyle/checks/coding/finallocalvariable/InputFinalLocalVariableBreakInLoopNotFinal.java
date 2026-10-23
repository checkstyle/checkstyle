/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class InputFinalLocalVariableBreakInLoopNotFinal {

    void noBreak(List<String> list) {
        String last;
        for (String element : list) {
            if (!element.isEmpty()) {
                last = element;
            }
        }
    }

    void breakInsideSwitch(int[] values) {
        int a;
        for (int value : values) {
            switch (value) {
                case 0:
                    a = 1;
                    break;
                default:
                    break;
            }
        }
    }

    void fallThroughInsideSwitch(int[] values) {
        int a;
        for (int value : values) {
            switch (value) {
                case 0:
                    a = 1;
                default:
            }
        }
    }

    void breakOnlyInnerLoop(List<List<String>> lists) {
        String first;
        for (List<String> list : lists) {
            for (String element : list) {
                if (!element.isEmpty()) {
                    first = element;
                    break;
                }
            }
        }
    }

    void continueBeforeBreak(List<String> list) {
        String first;
        for (String element : list) {
            if (!element.isEmpty()) {
                first = element;
                if (element.length() > 1) {
                    continue;
                }
                break;
            }
        }
    }

    void labeledBreakOfBlock(List<String> list) {
        String first;
        for (String element : list) {
            block: {
                first = element;
                break block;
            }
        }
    }

    void assignedAgainAfterLoop(List<String> list) {
        String first;
        for (String element : list) {
            if (!element.isEmpty()) {
                first = element;
                break;
            }
        }
        first = "";
    }

    void assignedInBranchWithoutBreak(List<String> list) {
        String first;
        for (String element : list) {
            if (!element.isEmpty()) {
                first = element;
            }
            else {
                break;
            }
        }
    }

    void retryInTryCatch(BlockingQueue<String> queue) {
        String element;
        while (true) {
            try {
                element = queue.take();
                break;
            }
            catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }
}
