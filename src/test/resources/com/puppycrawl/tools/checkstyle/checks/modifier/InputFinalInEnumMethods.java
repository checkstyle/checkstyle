package com.puppycrawl.tools.checkstyle.checks.modifier;

public enum InputFinalInEnumMethods {
    E1,
    E2 {
        @Override
        public final void v() {
        }
    };

    public void v() {
    }

    // not redundant since field can override this method if 'final' is removed
    // and that may not be desirable
    public final void v2() {
    }
}
enum InputFinalInEnumMethods2 {
    E1 {
        @Override
        public final void v() {
        }
    },
    E2 {
        @Override
        public void v() {
        }
    };

    public abstract void v();
}
