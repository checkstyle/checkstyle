/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

import java.util.ArrayList;

public final class InputFinalClass2
{
    private InputFinalClass2() {}
}

enum testenum1
{
    A, B;
    testenum1() {}
}

enum testenum2
{
    A, B;
    // violation below 'Class someinnerClass should be declared as final'
    public static class someinnerClass
    {
        private someinnerClass() {}
    }
}

interface TestInterface {
    class SomeClass { // violation 'Class SomeClass should be declared as final'
        private SomeClass() {}
    }
}

@interface SomeAnnotation {
    class SomeClass { // violation 'Class SomeClass should be declared as final'
        private SomeClass() {}
    }
}

class TestAnonymousInnerClasses {
    public static final TestAnonymousInnerClasses ONE = new TestAnonymousInnerClasses() {
        @Override
        public int value() {
            return 1;
        }
    };

    private TestAnonymousInnerClasses() {
    }

    public int value() {
        return 0;
    }
}

class TestNewKeyword { // violation 'Class TestNewKeyword should be declared as final'

    private TestNewKeyword(String s) {
        String a = "hello" + s;
    }

    public int count() {
        return 1;
    }
    public static final TestNewKeyword ONE = new TestNewKeyword("world");
    public static final test6 TWO = new test6() {
    };

    public static void main(String[] args) {
        ArrayList<String> foo = new ArrayList<>();
        foo.add("world");
        foo.forEach(TestNewKeyword::new);
    }
}

interface TestNewKeywordInsideInterface {
    ArrayList<String> foo = new ArrayList<>();
}

// abstract classes cannot be final
abstract class TestPrivateCtorInAbstractClasses {
    private TestPrivateCtorInAbstractClasses() {
    }
}

class TestAnonymousInnerClassInsideNestedClass {
    private TestAnonymousInnerClassInsideNestedClass() { }

    static class NestedClass { // violation 'Class NestedClass should be declared as final'

        public final static TestAnonymousInnerClassInsideNestedClass ONE
                = new TestAnonymousInnerClassInsideNestedClass() {
        };

        private NestedClass() {}
    }
    static class NestedClass2 {

        private NestedClass2() {}

        public static final test6  ONE = new test6() {

            public final NestedClass2 ONE = new NestedClass2() {

            };
        };
    }
}

