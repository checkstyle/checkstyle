/*
GenericWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import java.util.Optional;
import java.util.function.Supplier;


public class InputGenericWhitespaceMethodRef2
{
    final Supplier<Optional<Integer>> function1 = Optional::<Integer>empty;
    Supplier f2 = Optional::<Integer> empty; // violation ''>' is followed by whitespace.'
}
