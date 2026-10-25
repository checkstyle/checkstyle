/*
ClassTypeParameterName
format = ^[A-Z]{2,}$


*/

package org.checkstyle.suppressionxpathfilter.naming.classtypeparametername;

interface InputXpathClassTypeParameterNameInterface {

    class MyClass1<LISTENER> {
        public void myTest1() {}
    }

    class MyClass2<aBc> { // violation 'Name 'aBc' must match pattern '^[A-Z]{2,}$'.'
        public void myTest2() {}
    }

    class MyClass3<REQUESTA> {
        public void myTest3() {}
    }
}
