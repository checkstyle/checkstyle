package org.checkstyle.suppressionxpathfilter.superfinalize;

import com.puppycrawl.tools.checkstyle.checks.coding.superfinalize.InputSuperFinalizeMethodReference.CheckedConsumer;

public class InputXpathSuperFinalizeMethodReference extends ClassWithFinalizer {
    @Override
    protected void finalize() throws Throwable {
        CheckedConsumer<Throwable> r = super::finalize;
        r.get();
    }
}

class ClassWithFinalizer {
    protected void finalize() throws Throwable { // warn
    }
}
