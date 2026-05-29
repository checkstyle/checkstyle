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

class InvalidExample { // violation 'Class should define a constructor'

    public void test() {}

}

abstract class AbstractExample {

    public abstract void test();

}
