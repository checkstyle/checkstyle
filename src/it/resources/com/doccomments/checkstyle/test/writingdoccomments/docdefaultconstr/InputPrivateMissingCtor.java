package com.doccomments.checkstyle.test.writingdoccomments.docdefaultconstr;

public class InputPrivateMissingCtor {

    private int a;

    private InputPrivateMissingCtor(int a) {
        this.a = a;
    }

}

class ExampleDefaultCtor {

    private String s;

    private ExampleDefaultCtor() {
        s = "string";
    }

}

// violation 3 lines below 'Class should define an explicit constructor.
//      If this class was already released with an implicit constructor,
//      preserve its generated access modifier for compatibility.'
class InvalidExample {

    private void test() {}

}
