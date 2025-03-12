/*
SuperClone


*/

package com.puppycrawl.tools.checkstyle.checks.coding.superclone;

interface InputSuperClonePlainAndSubclasses {
    void clone();
}

class A {
  public Object clone() { // violation "Overriding clone() method must invoke super.clone() to ensure proper finalization."
      return null;
  }
}

class B{
  public Object clone() throws CloneNotSupportedException {
      super.clone();
      return null;
  }
  void clone(Object asd, Object asd2) {
  }
}

class C extends B {
  void method() throws CloneNotSupportedException {
    Object asd = null;
    super.clone(asd,asd);
    super.clone();
    Runnable a = () -> super.clone(null, null);
  }

  void method2() {
    new Runnable() {
      @Override
      public void run() {
        C.super.clone(null, null);
      }
    };
  }
}

class D extends B {
  // violation below "Overriding clone() method must invoke super.clone() to ensure proper finalization."
  public Object clone() throws CloneNotSupportedException {
    super.clone(null, null);
    return null;
  }

}
