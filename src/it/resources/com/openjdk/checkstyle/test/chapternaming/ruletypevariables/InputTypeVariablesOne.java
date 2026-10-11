package com.openjdk.checkstyle.test.chapternaming.ruletypevariables;

// violation first line 'Header mismatch'

public class InputTypeVariablesOne {

    class MyClass1<T> {}

    class MyClass2<t> {} // violation 'Name 't' must match pattern'

    class MyClass3<abc> {} // violation 'Name 'abc' must match pattern'

    class MyClass4<LISTENER> {}

    class MyClass5<K1, K2> {}

    interface FirstInterface<T> {}

    interface SecondInterface<t> {} // violation 'Name 't' must match pattern'

    interface ThirdInterface<type> {} // violation 'Name 'type' must match pattern'

    interface FourthInterface<VERTEX_ONE> {}

    interface FifthInterface<K1, K2> {}

    record Record1<T>() {}

    record Record2<t>() {} // violation 'Name 't' must match pattern'

    record Record3<abc>() {} // violation 'Name 'abc' must match pattern'

    record Record4<K1, K2>() {}

    public <T> void method1() {}

    public <a> void method2() {} // violation 'Name 'a' must match pattern'

    public <VERTEX_ONE, DST_VERTEX> void method3() {}

    public <k, V> void method4() {} // violation 'Name 'k' must match pattern'

}
