package com.google.checkstyle.test. //warn
              chapter3filestructure.rule332nolinewrap;

import com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck; //ok

import javax.accessibility. //warn
    AccessibleAttributeSequence;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater; //ok

import static java.math. //warn
        BigInteger.ONE;

public class
    InputNoLineWrapBad {

    public void
        fooMethod() {
        final int
            foo = 0;
    }
}

enum
    FooFoo {
}

interface
    InterFoo {}

