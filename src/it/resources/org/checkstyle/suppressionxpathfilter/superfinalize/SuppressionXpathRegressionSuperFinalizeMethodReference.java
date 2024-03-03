package org.checkstyle.suppressionxpathfilter.superfinalize;

public class SuppressionXpathRegressionSuperFinalizeMethodReference extends ClassWithFinalizer {

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
    protected void finalize() throws Throwable { // violation
    }
}