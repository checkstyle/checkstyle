package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.util.function.Function;
import java.util.function.Supplier;

class InputWhitespaceAroundEmptyTypesAndCycles
{
    private void foo()
    {
        int i = 0;
        String[][] x = { {"foo"} };
        for (int first = 0; first < 5; first++) {} //ok
        int j = 0;
        while (j == 1) {} //ok
        do {} while (i == 1); //ok
    }
}

interface SupplierFunction<T> extends Function<Supplier<T>, T> {} //ok

class EmptyFoo {} //ok

enum EmptyFooEnum {} //ok
