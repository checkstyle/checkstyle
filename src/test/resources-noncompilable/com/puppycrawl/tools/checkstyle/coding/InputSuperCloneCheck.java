// Requires Java8
package com.puppycrawl.tools.checkstyle.coding;

interface InputSuperCloneCheck {
    void clone();
}

class A {
  static{
      Runnable a = () -> super.clone();
  }
  
  void clone() {
      obj.super();
  }
}

class B{
  void clone() {
      (super).clone();
  }
}

class C {
  void method() {
    super();
    super.clone(asd,asd);
    super.clone();
  }
}
