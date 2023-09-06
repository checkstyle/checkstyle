package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.util.Arrays;
import java.util.List;

public class InputAntlr4AstRegressionLambda {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
    Integer value = Integer.valueOf(1);
    {
        numbers.forEach((Integer value) -> String.valueOf(value));
    }
}
