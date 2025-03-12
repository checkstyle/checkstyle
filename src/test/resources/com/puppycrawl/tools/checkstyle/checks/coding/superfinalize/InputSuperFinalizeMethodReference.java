/*
SuperFinalize


*/

package com.puppycrawl.tools.checkstyle.checks.coding.superfinalize;

public class InputSuperFinalizeMethodReference extends ClassWithFinalizer {

    public interface CheckedConsumer<E extends Throwable> {
        void get() throws E;
    }

    @Override
    protected void finalize() throws Throwable {
        CheckedConsumer<Throwable> r = super::finalize;
        r.get();
    }
}

class ClassWithFinalizer {
    protected void finalize() throws Throwable { // violation "Overriding finalize() method must invoke super.finalize() to ensure proper finalization."
    }
}
