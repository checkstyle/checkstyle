/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = (default)ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, \
         DOT, ARRAY_DECLARATOR, INDEX_OP


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.util.function.Function;
import java.util.function.IntFunction;

public class InputNoWhitespaceAfterTestMethodRef
{
    IntFunction<int[]> arrayMaker = int []::new; // violation
    Function<Integer, String[]> messageArrayFactory = String []::new; // violation
}
