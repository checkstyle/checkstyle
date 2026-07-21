package com.doccomments.checkstyle.test.writingdoccomments.docdefaultconstr;

public class InputPrivateMissingCtor {

    private int a;

    private InputPrivateMissingCtor(int a) {
        this.a = a;
    }

}

class ExampleDefaultCtor1 {

    private String s;

    private ExampleDefaultCtor1() {
        s = "string";
    }

}

// violation 3 lines below 'Class should define an explicit constructor.
//      If this class was already released with an implicit constructor,
//      preserve its generated access modifier for compatibility.'
class InvalidExample1 {

    private void test() {}

}
