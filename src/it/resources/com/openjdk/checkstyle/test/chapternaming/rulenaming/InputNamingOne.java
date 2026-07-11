package com.openjdk.checkstyle.test.chapternaming.rulenaming;

public class InputNamingOne {
    private String field;
    private String testField;

    InputNamingOne(String testField) { // violation, 'field' hides a field
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
        abstract int method(String field); // violation, ''field' hides a field'
    }
}
