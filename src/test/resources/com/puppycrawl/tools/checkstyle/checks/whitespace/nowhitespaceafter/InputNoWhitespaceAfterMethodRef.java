package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.util.function.Function;
import java.util.function.IntFunction;


public class InputNoWhitespaceAfterMethodRef
{
    IntFunction<int[]> arrayMaker = int []::new;//incorrect 10:40
    Function<Integer, String[]> messageArrayFactory = String []::new;//incorrect 11:63
}
