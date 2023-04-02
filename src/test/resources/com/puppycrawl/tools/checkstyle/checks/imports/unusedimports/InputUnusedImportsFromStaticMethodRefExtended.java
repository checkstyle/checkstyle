/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Optional; // ok

import java.util.function.Function; // ok
import java.util.List; // ok
import java.util.Arrays; // ok
import java.util.Objects; // violation
import static java.util.Arrays.asList; // violation

public class InputUnusedImportsFromStaticMethodRefExtended {

    InputUnusedImportsFromStaticMethodRefExtended() {
    }

    void testMethodRefWithQualifiedName()
    {
        Optional<String> test = Optional.empty();
        test.map(java.util.Objects::nonNull);
    }

    void testMethodRefWithGenericType()
    {
        Function<String[],List<String>> listGetter = Arrays::<String>asList;
    }

}
