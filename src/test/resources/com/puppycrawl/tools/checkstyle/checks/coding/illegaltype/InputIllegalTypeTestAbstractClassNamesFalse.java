package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

/*
 * Config:
 * validateAbstractClassNames = false
 */
public class InputIllegalTypeTestAbstractClassNamesFalse {

    abstract class AbstractClass {
        abstract String getClassInfo();
        abstract boolean isPerfectClass();
    }

    class MyNonAbstractClass extends AbstractClass { // ok

        boolean perfect = true;

        private MyNonAbstractClass() {}

        @Override
        String getClassInfo() {
            return "This is my non abstract class.";
        }

        @Override
        boolean isPerfectClass() {
            return perfect;
        }
    }

    AbstractClass a = new MyNonAbstractClass(); // ok

    public String getInnerClassInfo(AbstractClass clazz) { // ok
        return clazz.getClassInfo();
    }

    public AbstractClass newInnerClassInstance() { // ok
        return new MyNonAbstractClass();
    }
}
