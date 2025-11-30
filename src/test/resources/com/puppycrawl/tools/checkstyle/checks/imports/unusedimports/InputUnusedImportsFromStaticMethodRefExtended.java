/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)REFERENCE, PARAMETER_TYPE, THROWS_BLOCK_TAG

*/
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Optional;

import java.util.function.Function;
import java.util.List;
import java.util.Arrays;
import java.util.function.Predicate;

import java.util.Objects; // violation 'Unused import - java.util.Objects.'
import static java.util.Arrays.toString; // violation 'Unused import - java.util.Arrays.toString.'
import static java.util.Arrays.asList; // violation 'Unused import - java.util.Arrays.asList.'
import static java.lang.Integer.parseInt; // violation 'Unused import - java.lang.Integer.parseInt.'
import static java.util.Collections.emptyList; // violation 'Unused import - java.util.Collections.emptyList.'

public class InputUnusedImportsFromStaticMethodRefExtended {

    private Function<int[], String> arrayToString = Arrays::toString;
    Function<String, Integer> parseIntFunc = Integer::parseInt;
    private final Predicate<List> isListEmpty = List::isEmpty;


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
