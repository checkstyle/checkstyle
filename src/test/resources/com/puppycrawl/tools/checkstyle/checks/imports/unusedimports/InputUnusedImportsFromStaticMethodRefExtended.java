/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Optional;

import java.util.function.Function;
import java.util.List;
import java.util.Arrays;
import java.util.Objects; // violation 'Unused import - java.util.Objects.'
import static java.util.Arrays.asList; // violation 'Unused import - java.util.Arrays.asList.'

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
