package com.openjdk.checkstyle.test.chapternaming.rulenaming;

// violation first line 'Header mismatch'

public class InputNamingOne {
    private String field;
    private String testField;

    InputNamingOne(String testField) { // ok, constructor param is allowed
    }

    void method(String param) {
        String field = param; // violation, ''field' hides a field'
    }

    void setTestField(String testField) { // violation, ''testField' hides a field'
        this.field = field;
    }

    void setField(String field) { // violation, ''field' hides a field'
        this.field = field;
    }

    abstract class Inner {
        abstract int method(String field); // ok, abstract method parameter are allowed
    }
}
