/*
ChainedMethodCallWrap
identifierPattern = ^obj.*$
maxCallsPerLine = (default)1
maxSingleLineCalls = (default)1

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.chainedmethodcallwrap;

public class InputChainedMethodCallWrapTypecast {

    void testMethod() {
        ClassUtil1 obj = ClassUtil1.get();
        ((subClass<String>)((subClass<Integer>)obj).setVal1(1).setVal2(2)).setVal2(3);
                            // violation above ''3' method calls on single line, max allowed is '1''

        ((subClass<ClassUtil1>)((subClass) obj).setVal2(2)
            .setVal1(2).setVal1(3)) // violation 'Chained method call should be wrapped'
            .addAnotherCompo(ClassUtil1.get().setVal1(1).setVal2(2)); // ok

        ((subClass) obj.setVal1(2) // ok
            .setVal1(1))
            .setVal3(3)
            .setVal4(4);
    }
}

class ClassUtil1 {

    static ClassUtil1 get() {
        return new ClassUtil1();
    }

    ClassUtil1 setVal1(int val1) {
        return this;
    }

    ClassUtil1 setVal2(int val2) {
        return this;
    }

    ClassUtil1 setVal3(int val3) {
        return this;
    }

    ClassUtil1 setVal4(int val4) {
        return this;
    }

    ClassUtil1 addAnotherCompo(ClassUtil1 classUtil1) {
        return this;
    }
}

class subClass<T> extends ClassUtil1 {
}
