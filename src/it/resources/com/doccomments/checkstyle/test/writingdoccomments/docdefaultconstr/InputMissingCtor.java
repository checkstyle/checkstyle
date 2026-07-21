package com.doccomments.checkstyle.test.writingdoccomments.docdefaultconstr;

public class InputMissingCtor {

    private int a;

    InputMissingCtor(int a) {
        this.a = a;
    }

}

class ExampleDefaultCtor {

    private String s;

    ExampleDefaultCtor() {
        s = "string";
    }

}
// violation 3 lines below 'Class should define an explicit constructor.
//      If this class was already released with an implicit constructor,
//      preserve its generated access modifier for compatibility.'
class InvalidExample {

    public void test() {}

}

abstract class AbstractExample {

    public abstract void test();

}
