/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalClass"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

// xdoc section -- start
public class Example1 { // ok, since it has a public constructor

  final class A {
    private A() {
    }
  }

  class B { // violation, 'Class B should be declared as final.'
    private B() {
    }
  }

  class C { // ok, since it has a public constructor
    int field1;
    String field2;

    private C(int value) {
      this.field1 = value;
      this.field2 = " ";
    }

    public C(String value) {
      this.field2 = value;
      this.field1 = 0;
    }
  }

  class AnonymousInner { // ok, class has an anonymous inner class.
    public final AnonymousInner ONE
            = new AnonymousInner() {
            };

    private AnonymousInner() {
    }
  }

  class Class1 {

    private class Class2 { // violation, 'Class Class2 should be declared as final'
    }

    public class Class3 {
    }

  }
}
// xdoc section -- end
