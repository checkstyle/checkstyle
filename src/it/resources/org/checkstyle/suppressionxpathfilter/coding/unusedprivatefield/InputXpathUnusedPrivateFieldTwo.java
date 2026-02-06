package org.checkstyle.suppressionxpathfilter.coding.unusedprivatefield;

public class InputXpathUnusedPrivateFieldTwo {

    private int unusedPrivate; // warn

    private int usedPrivate; // ok

    private static final int CONSTANT = 42; // ok

    protected int protectedField; // ok as it trigger on private

    private int usedInInner; //ok

    public void foo() {
        System.out.println(usedPrivate);

        class Inner {
            void bar() {
                System.out.println(usedInInner);
            }
        }

        Inner inner = new Inner();
        inner.bar();
    }
}
