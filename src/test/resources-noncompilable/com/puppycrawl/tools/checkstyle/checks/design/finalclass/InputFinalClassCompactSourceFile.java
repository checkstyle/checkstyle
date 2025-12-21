/*
FinalClass


*/

// non-compiled with javac: Compilable with Java25

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

void xyz() {
    new WeakReference<>(null) {};
    new WeakHashMap<>() {};
}

class UtilityHolder { // violation 'Class UtilityHolder should be declared as final'
    private UtilityHolder() {}
}

class PublicCtorOk { // ok, has a public constructor
    public PublicCtorOk() {}
}

class DerivedExtendsBase extends BaseNotFinal {
    DerivedExtendsBase() {}
}

class BaseNotFinal { // ok, extended in same file
    private BaseNotFinal() {}
}

class Outer { // violation 'Class Outer should be declared as final'
    private Outer() {}

    class InnerCandidate { // violation 'Class InnerCandidate should be declared as final'
        private InnerCandidate() {}
    }

    final class InnerFinalOk {
        private InnerFinalOk() {}
    }
}

void main() {
    new PublicCtorOk();
    new DerivedExtendsBase();
    Outer o = new Outer();
    o.new InnerFinalOk();
}
