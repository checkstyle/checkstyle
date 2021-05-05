package com.puppycrawl.tools.checkstyle.utils.scopeutil;

/*
 * Config: default
 */
public interface InputScopeUtilInterface { // ok

    public static void foo1() {} // Scope = public

    public interface InnerInterface {

        public class InnerClassOne {
            class InnerClassTwo {
                public void foo3() {} // Scope = public
            }
        }
    }

    interface InnerInterfaceTwo{
        public class InnerClass {
            public void foo1() {} // Scope = package
        }
    }
}

class SubClass {
    public interface InnerInterfaceThree {
        public class InnerClassTwo {
            public void foo2() {} // Scope = package
        }
    }
}
