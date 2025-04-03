/*
HiddenField
ignoreConstructorParameter = true
ignoreSetter = true
setterCanReturnItsClass = true
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, PATTERN_VARIABLE_DEF, LAMBDA, RECORD_COMPONENT_DEF
ignoreFormat = (default)
ignoreAbstractMethods = (default)false

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.time.Clock;
import java.time.Instant;

public class InputHiddenFieldRecordsImplicitlyStaticClassComparison {
}

class Test {
    private final int x;

    public Test(int x) {
        this.x = x;
    }

    // inner record is implicitly static, should be treated
    // the same as R2
    public record R1() {
        public void m(int x) {}
    }

    public static record R2() {
        public void m(int x) {}
    }

    // static class, should be treated the same as R2
    public static class C1 {
        public void m(int x) {}
    }

    // non-static class, should have a violation
    public class C2 {
        public void m(int x) {} // violation ''x' hides a field'
    }

    record Wobble (Foo foo) implements ThrowingExternalizable {
        @Override
        public void writeExternal(ObjectOutput objectOutput) throws IOException {

        }

        @Override
        public void readExternal(ObjectInput objectInput)
                throws IOException, ClassNotFoundException {
        }
    }

    interface ThrowingExternalizable extends Externalizable {

    }

    class Foo {}

    public void test() {
        Foo foo = new Foo();
        Wobble wobble = new Wobble(foo);
        Wobble[] objToSerialize = new Wobble[] { wobble, wobble, wobble, wobble };
    }

}

