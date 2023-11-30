/*
ChainedMethodCallWrap
identifierPattern = ^ClassUtil.*$
maxCallsInMultiLine = 2
maxCallsInSingleLine = 3

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.chainedmethodcallwrap;

public class InputChainedMethodCallWrapNested {

    void method() {
        ClassUtil.get().setVal1(1).setVal2(2);

        ClassUtil.get().setVal1(1).setVal2(1).setVal3(1);
                            // violation above '4 method calls on single line, max allowed is 3'
        ClassUtil.get().setVal1(1)
                .addAnotherCompo(ClassUtil.get().setVal1(1)
                        .setVal2(1).setVal4(1).setVal3(1))
                        // violation above 'Wrap chained method call, max allowed on a line is 2'
                .setVal1(2)
                .setVal2(3);
    }

    void anotherMethod() {
        ClassUtil.get()
                .addAnotherCompo(ClassUtil.get().setVal1(1).setVal1(2).setVal2(2));
                            // violation above '4 method calls on single line, max allowed is 3'

        ClassUtil.get().setVal1(1).setVal2(2).setVal4
                    // violation above 'Wrap chained method call, max allowed on a line is 2'
            (3);    // considered violation as this is multiline.

        ClassUtil.get().setVal1(1)
                .addAnotherCompo(ClassUtil.get().setVal1(2)
                   .setVal3(2).setVal3(3).addAnotherCompo(
                      ClassUtil.get() // violation above 'Wrap chained method call, max .* is 2'
                      .setVal3(2).setVal4(4).setVal3(2)));
                       // violation above 'Wrap chained method call, max allowed on a line is 2'
    }

}

class ClassUtil {

    static ClassUtil get() {
        return new ClassUtil();
    }

    ClassUtil setVal1(int val1) {
        return this;
    }

    ClassUtil setVal2(int val2) {
        return this;
    }

    ClassUtil setVal3(int val3) {
        return this;
    }

    ClassUtil setVal4(int val4) {
        return this;
    }

    ClassUtil addAnotherCompo (ClassUtil classUtil) {
        return this;
    }
}
