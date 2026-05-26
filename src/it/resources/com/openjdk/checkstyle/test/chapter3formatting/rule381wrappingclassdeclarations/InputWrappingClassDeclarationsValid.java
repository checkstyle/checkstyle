package com.openjdk.checkstyle.test.chapter3formatting.rule381wrappingclassdeclarations;

import java.util.HashMap;

public abstract class InputWrappingClassDeclarationsValid<T, S>
        extends HashMap<T, S>
        implements Comparable<T> {
}

class AnotherValidClass<
        K,
        R,
        V> {
}
