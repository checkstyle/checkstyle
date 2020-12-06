package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.AbstractMap; // ok
import java.util.List; // ok
import java.util.Map; // violation
import java.util.Set; // violation
import java.util.concurrent.Callable; // ok

import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed.Nested; // ok
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed.Nested.List.Map.Entry; // ok

/*
 * Config: default
 */
public class InputUnusedImportsShadowed
        extends AbstractMap.SimpleEntry
        implements Comparable<Nested.List.Map.Entry>, Callable<Entry> {

    InputUnusedImportsShadowed() {
        super(null, null);
        List x = null; // java.util.List
    }

    @Override
    public int compareTo(Nested.List.Map.Entry entry) {
        return 0;
    }

    @Override
    public Entry call() throws Exception {
        return Entry.IGNORED;
    }

    static class Nested {
        List.Map field; // Nested.List.Set
        public Nested() {
            List x = null; // Nested.List
        }

        @Set
        interface List {
            Map foo(); // Nested.List.Set
            class Map {
                enum Entry {
                    BAR, FOO, IGNORED
                }
            }
        }
    }

    @interface Set {
    }

    interface Callable<V> {
        V call();
    }

    interface AbstractMap {
        class SimpleEntry {
        }
    }

}
