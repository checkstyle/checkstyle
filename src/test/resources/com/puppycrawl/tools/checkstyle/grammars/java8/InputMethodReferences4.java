//Issue #2729
package com.puppycrawl.tools.checkstyle.grammars.java8;
import java.util.Arrays;


public class InputMethodReferences4 {
    public void doSomething(final Object... arguments) {
        Arrays.stream(arguments)
                .map(Object::getClass)
                .toArray(Class<?>[]::new);
    }
}
