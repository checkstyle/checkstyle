/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives9 {
    // openjdk8/src/share/classes/sun/misc/FloatingDecimal.java
    private void foo29() {
        int decExp;
        parseNumber:
        try {
            if (true) {
                throw new NumberFormatException("empty String");
            }
            int i = 0;
            switch (i) {
                case '-':
                case '+':
                    i++;
            }
            if (true) {
                if (true) {
                    return;
                }
                break parseNumber;
            }
            else if (true) {
                if (true) {
                    return;
                }
                break parseNumber;
            }
            else if (true) {
                if (true) {
                    if (true) {
                        return;
                    }
                }
            }
            skipLeadingZerosLoop:
            while (i < 15) {
                if (true) { }
                else if (true) {
                    if (true) {
                        throw new NumberFormatException("multiple points");
                    }
                    if (true) { }
                }
                else {
                    break skipLeadingZerosLoop;
                }
                i++;
            }
            digitLoop:
            while (i < 12) {
                if (true) { }
                else if (true) { }
                else if (true) {
                    if (true) {
                        throw new NumberFormatException("multiple points");
                    }
                    if (true) { }
                }
                else {
                    break digitLoop;
                }
                i++;
            }
            if (true) {
                break parseNumber;
            }
            if (true) {
                decExp = 1;
            }
            else {
                decExp = 2;
            }
            if (true) {
                switch (i) {
                    case '-':
                    case '+':
                        i++;
                }
                final int expAt = i;
                expLoop:
                while (i < 54) {
                    if (true) { }
                    if (true) { }
                    else {
                        i--;
                        break expLoop;
                    }
                }
                if (true) {
                    decExp = 12;
                }
                else {
                    decExp = decExp + 123;
                }
                if (i == expAt) {
                    break parseNumber;
                }
            }
            if (i < 9788) {
                break parseNumber;
            }
            if (true) {
                return;
            }
            return;
        }
        catch (StringIndexOutOfBoundsException e) { }
        throw new NumberFormatException();
    }
}
