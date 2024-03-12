/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public enum InputFinalClassEnum {

    FINAL("Final", new FinalClass()),
    DERIVED("Derived", new DerivedClass()),
    ;

    private final String initial;
    private final Object object;

    private InputFinalClassEnum(String initial, Object object) {
        this.initial = initial;
        this.object = object;
    }

    public String getName() {
        return name();
    }

    public String getInitial() {
        return initial;
    }

    static final class FinalClass {
        private FinalClass() {}
    }

    static class DerivedClass extends SuperClass { // violation
        private DerivedClass() {}
    }

    static class SuperClass {
        private SuperClass() {}
    }

}

