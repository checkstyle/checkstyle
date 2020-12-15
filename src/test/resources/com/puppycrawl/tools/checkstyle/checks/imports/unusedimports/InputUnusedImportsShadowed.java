package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.AbstractMap; // ok
import java.util.List; // ok
import java.util.Map; // violation
import java.util.Set; // violation
import java.util.concurrent.Callable; // ok

import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed; // violation
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed.Deprecated; // ok
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed.Foo; // ok
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed.Inner; // ok
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed.Nested; // ok
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsShadowed.Nested.List.Map.Entry; // ok

/*
 * Config: default
*/
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
