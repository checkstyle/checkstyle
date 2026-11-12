/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.util.List;
import java.util.function.Consumer;

public class InputUnusedPrivateFieldVar {

    static class Entry {
        private int hits; // ok, used in enhanced-for with var
    }

    static int total(List<Entry> entries) {
        int total = 0;
        for (var entry : entries) {
            total += entry.hits;
        }
        return total;
    }

    static class Entry2 {
        private int count; // ok, used via var local variable
    }

    static int getCount(Entry2 e) {
        var entry = e;
        return entry.count;
    }

    static class Entry3 {
        private int size; // ok, used via var lambda parameter
    }

    static void process(Consumer<Entry3> action, Entry3 e) {
        final Consumer<Entry3> c = (var item) -> {
            System.out.println(item.size);
        };
    }

    static class ShadowTest {
        private int field; // violation 'Unused private field'

        void test() {
            var field = 10;
            System.out.println(field);
        }
    }

    static class TrulyUnused {
        private int unused; // violation 'Unused private field'

        void test() {
            var x = "hello";
            System.out.println(x.length());
        }
    }

    static class VarNamedVar {
        private int myField; // violation 'Unused private field'

        void test() {
            final int var = 10;
            System.out.println(var);
        }
    }
}
