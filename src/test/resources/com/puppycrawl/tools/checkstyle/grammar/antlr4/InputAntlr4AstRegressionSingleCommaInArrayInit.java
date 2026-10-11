package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionSingleCommaInArrayInit {
    @Foo({,}) void b() { }
}
@interface Foo { int[] value(); }
