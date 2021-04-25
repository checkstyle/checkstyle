package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.util.function.Function;
import java.util.function.IntFunction;

/*
 * Config: default
 */
public class InputNoWhitespaceAfterTestMethodRef
{
    IntFunction<int[]> arrayMaker = int []::new; // violation
    Function<Integer, String[]> messageArrayFactory = String []::new; // violation
}
