package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

/*
 * Config:
 * validateAbstractClassNames = true
 */
public class InputIllegalTypeTestAbstractClassNamesTrue {

    abstract class AbstractClass {
        abstract String getClassInfo();
        abstract boolean isPerfectClass();
    }

    class MyNonAbstractClass extends AbstractClass { // violation

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

    AbstractClass a = new MyNonAbstractClass(); // violation

    public String getInnerClassInfo(AbstractClass clazz) { // violation
        return clazz.getClassInfo();
    }

    public AbstractClass newInnerClassInstance() { // violation
        return new MyNonAbstractClass();
    }
}
