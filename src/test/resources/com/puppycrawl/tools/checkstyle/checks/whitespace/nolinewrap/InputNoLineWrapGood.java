/*
NoLineWrap
tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF, RECORD_DEF, COMPACT_CTOR_DEF
skipAnnotations = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;



import javax.accessibility.AccessibleAttributeSequence;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import static java.math.BigInteger.ZERO;

@SuppressWarnings("unused")
public class InputNoLineWrapGood {

    @Deprecated
    public InputNoLineWrapGood() {
    }

    @Deprecated
    public void fooMethod() {
        //
    }

    @Deprecated
    public record InputNoLineWrapGoodRecord(String foo) {
        @Deprecated
        public InputNoLineWrapGoodRecord {
        }
    }
}
