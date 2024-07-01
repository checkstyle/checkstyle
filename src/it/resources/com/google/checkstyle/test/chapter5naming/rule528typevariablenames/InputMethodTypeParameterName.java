package com.google.checkstyle.test.chapter5naming.rule528typevariablenames;

import java.io.Serializable;

class InputMethodTypeParameterName <T>
{
    public <TT> void foo() { }

    <e_e> void foo(int i) { // violation 'Method type name 'e_e' must match pattern'
    }
}

class Other2 <T extends Serializable & Cloneable> {

    T getOne() {
    return null;
    }

    <Tfo$o2T extends T> Tfo$o2T getTwo(Tfo$o2T a) {
    // violation above 'Method type name .* must match pattern'
    return null;
    }

    <foo_ extends Runnable> T getShadow() {
    // violation above 'Method type name 'foo_' must match pattern'
    return null;
    }

    static class Junk <E> {
        <_abc extends E> void getMoreFoo() {
            // violation above 'Method type name '_abc' must match pattern'
    }
    }
}

class MoreOther3 <T extends Cloneable> {

    <E extends T> void getMore() {
        new Other2() {
            <T$> void getMoreFoo() { // violation 'Method type name .* must match pattern'
        }
    };

        Other2 o = new Other2() {
            <EE> void getMoreFoo() { // violation 'Method type name 'EE' must match pattern'
            }
        };
    }
}
