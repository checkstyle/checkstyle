/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public enum InputFinalClassPrivateCtor3 {

    VALUE1("Value 1"),
    VALUE2("Value 2");

    private String value;
    private MyClass myPrivateClass;

    InputFinalClassPrivateCtor3(String value) {
        this.value = value;
        this.myPrivateClass = new MyClass();
    }

    public String getValue() {
        return value;
    }

    private static class MyClass { // violation 'Class MyClass should be declared as final'
        // implementation details
    }

    private class Check2 { // violation 'Class Check2 should be declared as final'
        private class Check3 { // violation 'Class Check3 should be declared as final'
        }
    }

    private class Check4 { // violation 'Class Check4 should be declared as final'
        protected class Class {
        }
    }

    private class Check {} // violation 'Class Check should be declared as final'
}
