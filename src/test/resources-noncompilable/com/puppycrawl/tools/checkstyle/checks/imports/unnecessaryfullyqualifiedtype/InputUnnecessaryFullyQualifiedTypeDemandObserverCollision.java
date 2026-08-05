/*
UnnecessaryFullyQualifiedType


*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.unnecessaryfullyqualifiedtype;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class InputUnnecessaryFullyQualifiedTypeDemandObserverCollision {

    static final class BlockingObservableIterator<T>
            extends AtomicReference<Object>
            implements io.reactivex.rxjava3.core.Observer<T>, Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }
    }

}
