package com.google.checkstyle.test.chapter5naming.rule528typevariablenames;

import java.io.Serializable;

class InputTypeParameterName <t> //warn
{
    public <TT> void foo() { }
    
    <e_e> void foo(int i) {
    }
}

class Other <foo extends Serializable & Cloneable> { //warn
    
    foo getOne() {
    return null;
    }
    
    <Tfo$o2T extends foo> Tfo$o2T getTwo(Tfo$o2T a) {
    return null;
    }
    
    <foo_ extends Runnable> foo getShadow() {
    return null;
    }
    
    static class Junk <$foo> { //warn
        <_abc extends $foo> void getMoreFoo() {
    }
    }
}

class MoreOther <T extends Cloneable> {
    
    <E extends T> void getMore() {
        new Other() {
            <T$> void getMoreFoo() {
        }
    };
    
        Other o = new Other() {
            <EE> void getMoreFoo() {
            }
        };
    }
}
