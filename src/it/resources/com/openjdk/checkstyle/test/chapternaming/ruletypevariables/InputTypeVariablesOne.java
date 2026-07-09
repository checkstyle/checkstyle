package com.openjdk.checkstyle.test.chapternaming.ruletypevariables;

public class InputTypeVariablesOne {

    class MyClass1<T> {}

    class MyClass2<t> {} // violation 'Name 't' must match pattern'

    class MyClass3<abc> {} // violation 'Name 'abc' must match pattern'

    class MyClass4<LISTENER> {}

    interface FirstInterface<T> {}

    interface SecondInterface<t> {} // violation 'Name 't' must match pattern'

    interface ThirdInterface<type> {} // violation 'Name 'type' must match pattern'

    interface FourthInterface<VERTEX_ONE> {}

    record Record1<T>() {}

    record Record2<t>() {} // violation, Name 't' must match pattern'

    record Record3<abc>() {} // violation, Name 'abc' must match pattern'

    public <T> void method1() {}

    public <a> void method2() {} // violation 'Name 'a' must match pattern'

    public <VERTEX_ONE, DST_VERTEX> void method3() {}

    public <k, V> void method4() {} // violation 'Name 'k' must match pattern'

}
