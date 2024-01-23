/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="GenericWhitespace"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

// xdoc section -- start
public class Example1 {

    // Generic methods definitions
    public static <K, V> boolean foo(K key, V value) {
        // Implementation details
        return false;
    }

    // Generic type definition
    class name<T1, T2> {
        // Implementation details
    }

    public void examples() {
        // Generic type reference
        OrderedPair<String, List<Integer>> p;

        // Generic preceded method name
        boolean same = Example1.<Object, Object>foo(null, null);

        // Diamond operator
        Map<Integer, String> p1;

        // Method reference
        List<String> list = ImmutableList.<String>builder().build();

        // Method reference
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // some logic
                return 0;
            }
        });

        // Constructor call
        MyClass<String> obj = new MyClass<>();
    }
}
// xdoc section -- end

// Duplicate class declarations removed
class OrderedPair<K, V> {
    // Implementation details
}

class ImmutableList {
    public static <T> Builder<T> builder() {
        // Implementation details
        return new Builder<>();
    }

    static class Builder<T> {
        public List<T> build() {
            // Implementation details
            return null;
        }
    }
}

class MyClass<T> {
    // Implementation details
}
