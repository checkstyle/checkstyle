package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

public class InputMissingJavadocTypeCheck { //warn

    public class Inner { // warn

    }

    public enum MyEnum { // warn

    }

    public interface MyInterface {  // warn
        class MyInterfaceClass {}  // warn
    }

    public @interface MyAnnotation { // warn

    }

    protected class InnerProtected { // warn

    }

    protected enum MyEnumProtected { // warn

    }

    protected interface MyInterfaceProtected {  // warn

    }

    protected @interface MyAnnotationProtected { //warn

    }

    public void myMethod() {
        class MyMethodClass {} // ok
    }

}

class AdditionalClass { // OK, not public

}
