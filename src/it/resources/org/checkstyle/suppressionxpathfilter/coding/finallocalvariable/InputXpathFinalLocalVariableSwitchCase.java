/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT, CTOR_DEF, METHOD_DEF, SLIST, OBJBLOCK, COMPACT_COMPILATION_UNIT, \
         LITERAL_BREAK, LITERAL_FOR, VARIABLE_DEF, EXPR


*/

package org.checkstyle.suppressionxpathfilter.coding.finallocalvariable;

public class InputXpathFinalLocalVariableSwitchCase {
    class InnerClass {
        public void method(final int i) {
            switch (i) {
                case 1:
                    // violation below 'Variable 'foo' should be declared final.'
                    int foo = 1;
                    break;
                default:
            }
            switch (i) {
                case 1:
                    int foo = 1;
                    break;
                case 2:
                    foo = 2;
                    break;
                default:
            }
        }
    }
}
