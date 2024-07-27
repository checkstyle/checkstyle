package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

public class InputMissingJavadocTypeIncorrect { // violation 'Missing a Javadoc comment.'

  public class Inner { // violation 'Missing a Javadoc comment.'
  }

  public enum MyEnum { // violation 'Missing a Javadoc comment.'
  }

  public interface MyInterface { // violation 'Missing a Javadoc comment.'
    class MyInterfaceClass {} // violation 'Missing a Javadoc comment.'
  }

  public @interface MyAnnotation { // violation 'Missing a Javadoc comment.'
  }

  protected class InnerProtected { // violation 'Missing a Javadoc comment.'
  }

  protected enum MyEnumProtected { // violation 'Missing a Javadoc comment.'
  }

  protected interface MyInterfaceProtected { // violation 'Missing a Javadoc comment.'
  }

  protected @interface MyAnnotationProtected { // violation 'Missing a Javadoc comment.'
  }

  public void myMethod() {
    class MyMethodClass {}
  }

  class AdditionalClass { // OK, not public
  }
}
