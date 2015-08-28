package com.puppycrawl.tools.checkstyle.coding;

public class InputIllegalTypeAbstractClassNames {

    abstract class AbstractClass {
        abstract String getClassInfo();
        abstract boolean isPerfectClass();
    }

    class MyNonAbstractClass extends AbstractClass {

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

    AbstractClass a = new MyNonAbstractClass();

    public String getInnerClassInfo(AbstractClass clazz) {
        return clazz.getClassInfo();
    }

    public AbstractClass newInnerClassInstance() {
        return new MyNonAbstractClass();
    }
}
