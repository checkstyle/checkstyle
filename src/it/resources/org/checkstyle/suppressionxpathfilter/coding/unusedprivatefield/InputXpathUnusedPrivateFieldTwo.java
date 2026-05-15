package org.checkstyle.suppressionxpathfilter.coding.unusedprivatefield;

public class InputXpathUnusedPrivateFieldTwo {

    private int unusedPrivate; // warn

    private int usedPrivate;

    private static final int CONSTANT = 42;

    protected int protectedField; // ok as it trigger on private

    private int usedInInner;

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
