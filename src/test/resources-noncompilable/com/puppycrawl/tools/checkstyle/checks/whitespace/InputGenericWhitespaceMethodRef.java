//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.whitespace;

public class InputGenericWhitespaceMethodRef
{
    final Supplier<Optional<Integer>> function = Optional::<Integer>empty;
    final Supplier<Optional<Integer>> function = Optional::<Integer> empty;
}