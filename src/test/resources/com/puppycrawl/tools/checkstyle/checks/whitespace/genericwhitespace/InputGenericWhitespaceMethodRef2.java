package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import java.util.Optional;
import java.util.function.Supplier;


public class InputGenericWhitespaceMethodRef2
{
    final Supplier<Optional<Integer>> function1 = Optional::<Integer>empty;
    final Supplier<Optional<Integer>> function2 = Optional::<Integer> empty;
}
