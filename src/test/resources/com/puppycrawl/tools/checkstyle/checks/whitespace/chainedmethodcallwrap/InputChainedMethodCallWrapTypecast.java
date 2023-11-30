/*
ChainedMethodCallWrap
identifierPattern = ^obj\.setVal1\.setVal4.*$
maxCallsInMultiLine = (default)1
maxCallsInSingleLine = (default)1

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.chainedmethodcallwrap;

public class InputChainedMethodCallWrapTypecast {

    void testMethod() {
        ClassUtil1 obj = ClassUtil1.get();
        ((subClass<String>)((subClass<Integer>)obj).setVal1(1).setVal4(2)).setVal2(3);
                            // violation above '3 method calls on single line, max allowed is 1'

        ((subClass<ClassUtil1>)((subClass) obj).setVal1(2)
            .setVal4(2).setVal1(3)) // violation 'Wrap chained method call, max allowed .* is 1'
            .addAnotherCompo(ClassUtil1.get().setVal1(1).setVal2(2));

        ((subClass) obj.setVal1(2)
            .setVal4(1))
            .setVal3(3)
            .setVal4(4);

        ((subClass) (obj.setVal1(1))).setVal4(2).setVal4(3);
                            // violation above '3 method calls on single line, max allowed is 1'

        ((subClass) ((obj.setVal1(1).setVal4(3).getArr()[0].setVal4(4)))).setVal1(1);
                            // violation above '5 method calls on single line, max allowed is 1'

        (((((((subClass) obj.setVal1(1).setVal4(3))))).setVal3(4).getArr()[1])).setVal4(3);
                            // violation above '5 method calls on single line, max allowed is 1'

        ((100000 + 0) + obj.setVal1(1).setVal4(3).toString()).substring(1);
                            // violation above '3 method calls on single line, max allowed is 1'

        // violation below '3 method calls on single line, max allowed is 1'
        ((subClass) (obj.setVal1(1).setVal4(2))).addAnotherCompo(
            ((subClass) ((subClass<ClassUtil1>) (obj.setVal1(1)
                .setVal4(3))).getArr()[0]).addAnotherCompo(
                // 2 violations above
                obj.setVal1(2).setVal4(3).addAnotherCompo(
                    // violation above '3 method calls on single line, max allowed is 1'
                    (subClass) (obj.setVal4(3)
                        .setVal2(2).setVal2(3)) // ok, identifierPattern is different
                )
            )
        );
    }
}

class ClassUtil1 {

    private ClassUtil1[] arr = {this};

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

    ClassUtil1[] getArr() {
        return arr;
    }
}

class subClass<T> extends ClassUtil1 {
}
