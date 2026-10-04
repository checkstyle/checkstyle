package com.openjdk.checkstyle.test.chapterformatting.rulewrappingclassdeclarations;

// violation first line 'Header mismatch'

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;

public class InputWrappingClassDeclarationsDoAndDonts {

    abstract class MyGenericClass<T, S>
            extends HashMap<T, S>
            implements Comparable<T> {
    }

    abstract class AnotherClass<K, R> implements Collector<K,
                                                        Set<? extends R>,
                                                        List<R>> {
    }

    abstract class MyGenericClassOne<T> implements Comparable<T>,
            // violation above 'should be on a new line'
            Predicate<T> {
    }
}
