//non-compiled with eclipse: 'The explicit 'this' parameter is expected to be qualified with Inner'
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionQualifiedConstructorParameter {
    class Inner {
        class Inner2 {
            Inner2(InputAntlr4AstRegressionQualifiedConstructorParameter.Inner
                           InputAntlr4AstRegressionQualifiedConstructorParameter.Inner.this) { }
        }
    }
}
