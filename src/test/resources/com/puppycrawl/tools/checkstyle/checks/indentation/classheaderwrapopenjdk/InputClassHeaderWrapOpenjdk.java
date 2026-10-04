/*
ClassHeaderWrapOpenjdk


*/
package com.puppycrawl.tools.checkstyle.checks.indentation.classheaderwrapopenjdk;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;

public class InputClassHeaderWrapOpenjdk {

    class NotWrapped extends Object implements Runnable {
        @Override
        public void run() {
        }
    }

    abstract static class CorrectlyWrapped<T, S>
            extends HashMap<T, S>
            implements Comparable<T> {
    }

    @SuppressWarnings("unused")
    abstract static class AnnotatedAndWrapped<T, S>
            extends HashMap<T, S>
            implements Comparable<T> {
    }

    abstract static class ImplementsAfterWrappedExtends<T, S>
            extends HashMap<T, S> implements Comparable<T> {
        // violation above """The 'implements' clause should be on a new line when the
        // class header is wrapped."""
    }

    abstract static class MultipleInterfacesWrapped<T> implements Comparable<T>,
            Runnable {
    }
    // violation 3 lines above """The 'implements' clause should be on a new line when the
    // class header is wrapped."""

    abstract static class NestedGenericException<K, R> implements Collector<K,
                                                                            Set<? extends R>,
                                                                            List<R>> {
    }

    abstract static class ExtendsOnSameLine extends Object implements Comparable<String>,
            Runnable {
        // violation 2 lines above """The 'implements' clause should be on a new line when
        // the class header is wrapped."""
    }

    static class UnnecessarilyWrappedExtends
            extends Object {
        // violation 2 lines above """The class header should not be wrapped as it fits
        // within the maximum column limit of 80."""
    }

    interface MultiExtendInterface extends Comparable<String>,
            Runnable {
    }
    // violation 3 lines above """The 'extends' clause should be on a new line when the
    // class header is wrapped."""

    enum ShortEnumWrapped
            implements Comparable<ShortEnumWrapped> {
        // violation 2 lines above """The class header should not be wrapped as it fits
        // within the maximum column limit of 80."""
        INSTANCE
    }

    record RecordWithImplements(int firstComponent, int secondComponent)
            implements Comparable<RecordWithImplements> {
        @Override
        public int compareTo(RecordWithImplements other) {
            return 0;
        }
    }

    record RecordWithWrappedComponents(int firstComponent,
            int secondComponent) implements Comparable<RecordWithWrappedComponents> {
        @Override
        public int compareTo(RecordWithWrappedComponents other) {
            return 0;
        }
    }

    static class WrappedTypeParamsOnly<VeryLongTypeParameterName,
            AnotherLongTypeParameterName> {
    }

    static class BlankLineInHeader

            extends Object {
        // violation 3 lines above """The class header should not be wrapped as it fits
        // within the maximum column limit of 80."""
    }
}
