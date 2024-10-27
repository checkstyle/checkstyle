package org.checkstyle.suppressionxpathfilter.classtypeparametername;

interface InputXpathClassTypeParameterNameInterface {

    class MyClass1<LISTENER> {
        public void myTest1() {}
    }

    class MyClass2<aBc> { // warn
        public void myTest2() {}
    }

    class MyClass3<REQUESTA> {
        public void myTest3() {}
    }
}
