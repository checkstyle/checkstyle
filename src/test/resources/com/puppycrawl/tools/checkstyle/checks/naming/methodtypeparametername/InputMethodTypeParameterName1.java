/*
MethodTypeParameterName
format = ^foo$


*/

package com.puppycrawl.tools.checkstyle.checks.naming.methodtypeparametername;

import java.io.Serializable;

public class InputMethodTypeParameterName1 <t>
{
    public <TT> void foo() { } // violation 'Name 'TT' must match pattern'

    <e_e> void foo(int i) { // violation 'Name 'e_e' must match pattern'
    }
}

class Other1 <foo extends Serializable & Cloneable> {

    foo getOne() {
        return null;//comment
    }

    <Tfo$o2T extends foo> /*comment*/Tfo$o2T getTwo(Tfo$o2T a) {
        // violation above 'Name 'Tfo\$o2T' must match pattern'
        return null;
    }
    <foo extends Runnable> foo getShadow() {
        return null;
    }

    static class Junk <foo> {
        <_fo extends foo> void getMoreFoo() { // violation 'Name '_fo' must match pattern'
        }
    }
}

class MoreOther1 <T extends Cloneable> {

    <E extends T> void getMore() { // violation 'Name 'E' must match pattern'
        new Other() {
            <T> void getMoreFoo() { // violation 'Name 'T' must match pattern'
            }
        };

//        Other o = new Other() {
//            <EE> void getMoreFoo() {
//            }
//        };
    }
}

interface Boo1<Input> {
    Input boo();
}

interface FooInterface1<T> {
    T foo();
}

interface FooInterface3 {
    Input foo();
}

class Input1 {

}
