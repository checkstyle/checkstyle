package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.util.Iterator;
import java.util.function.Function;

public class InputAntlr4AstRegressionTrickyYield<T1> {
    public Iterator<T1> yield() {
        return yield(Function.identity());
    }

    private static <T, T1> Iterator<T1> yield(Function<T,T> identity) {
        return null;
    }
}
