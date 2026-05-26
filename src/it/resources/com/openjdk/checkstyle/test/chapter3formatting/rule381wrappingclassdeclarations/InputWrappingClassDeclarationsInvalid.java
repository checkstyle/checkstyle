package com.openjdk.checkstyle.test.chapter3formatting.rule381wrappingclassdeclarations;

import java.util.HashMap;

public abstract class InputWrappingClassDeclarationsInvalid<T, S>
    extends HashMap<T, S> // violation '.* incorrect indentation level 4, expected .* 8.'
    implements Comparable<T> { // violation '.* incorrect indentation level 4, expected .* 8.'
}

class AnotherInvalidClass<
    K, // violation '.* incorrect indentation level 4, expected .* 8.'
    R, // violation '.* incorrect indentation level 4, expected .* 8.'
    V> { // violation '.* incorrect indentation level 4, expected .* 8.'
}
