/*
ClassTypeParameterName
format = (^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)


*/

package org.checkstyle.suppressionxpathfilter.naming.classtypeparametername;

public class InputXpathClassTypeParameterNameMethod {

    public void myTest1() {
        class MyClass1<T> {
            public void test() {}
        }
    }

    public void myTest2() {
    // violation below 'Name 'ABC' must match pattern '(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)'.'
        class MyClass2<ABC> {
            public void test() {}
        }
    }

    public void myTest3() {
    class MyClass3<RequestT> {
        public void test() {}
    }
}
}
