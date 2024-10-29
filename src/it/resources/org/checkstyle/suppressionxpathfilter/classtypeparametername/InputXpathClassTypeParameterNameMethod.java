package org.checkstyle.suppressionxpathfilter.classtypeparametername;

public class InputXpathClassTypeParameterNameMethod {

    public void myTest1() {
        class MyClass1<T> {
            public void test() {}
        }
    }

    public void myTest2() {
        class MyClass2<ABC> { // warn
            public void test() {}
        }
    }

    public void myTest3() {
    class MyClass3<RequestT> {
        public void test() {}
    }
}
}
