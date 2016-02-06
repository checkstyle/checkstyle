//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.Optional;
import java.util.function.Supplier;

public class InputGenericWhitespaceMethodRef
{
    final Supplier<Optional<Integer>> function1 = Optional::<Integer>empty;
    final Supplier<Optional<Integer>> function2 = Optional::<Integer> empty;
}