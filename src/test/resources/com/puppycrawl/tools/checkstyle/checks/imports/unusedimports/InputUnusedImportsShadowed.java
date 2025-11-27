/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map; // violation 'Unused import - java.util.Map.'
import java.util.Set; // violation 'Unused import - java.util.Set.'
import java.util.concurrent.Callable;

import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed;
// violation above 'Unused import - .*InputUnusedImportsShadowed.'
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed.Deprecated;
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed.Foo;
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed.Inner;
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed.Nested;
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed.Nested.List.Map.Entry;

@Deprecated(foo = Foo.class, classes = {Inner.class})
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
        List.Map field;
        public Nested() {
            List x = null;
        }

        @Set
        interface List {
            Map foo();
            class Map {
                enum Entry {
                    BAR, FOO, IGNORED
                }
            }
        }
    }

    @interface Set {
    }

    @interface Deprecated {
        Class<?> foo();
        Class<?>[] classes();
    }

    @interface Inner {
    }

    enum Foo {
    }

    interface Callable<V> {
        V call();
    }

    interface AbstractMap {
        class SimpleEntry {
        }
    }

    List field; // java.util.List

    void function() {
        List variable; // java.util.List
        {
            class List {
            }
        }
    }

}
