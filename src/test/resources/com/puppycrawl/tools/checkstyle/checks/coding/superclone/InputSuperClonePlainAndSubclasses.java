package com.puppycrawl.tools.checkstyle.checks.coding.superclone;


interface InputSuperClonePlainAndSubclasses {
    void clone();
}

class A {
  public Object clone() {
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
