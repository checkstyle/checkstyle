package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public abstract class InputRedundantModifierFinalInAbstractMethods {
    public abstract void method(final String param); // violation

    public abstract void method2(String param);

    public abstract void method3(String param1, final String param2); // violation
}
interface IWhatever {
    void method(final String param); // violation

    void method2(String param);
}
class CWhatever {
    native void method(final String param); // violation

    native void method2(String param);
}
enum EWhatever {
    TEST() {
        public void method(String s) {};
    };

    public abstract void method(final String s); // violation
}
