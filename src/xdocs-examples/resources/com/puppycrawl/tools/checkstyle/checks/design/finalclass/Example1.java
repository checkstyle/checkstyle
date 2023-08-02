/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalClass"/>
  </module>
</module>
*/

// xdoc section -- start
final class Example1 {
  private Example1() { }
}

class A { // violation, 'Class A should be declared as final.'
  private A() { }
}

class B { // ok, since it has a public constructor
  int field1;
  String field2;
  private B(int value) {
    this.field1 = value;
    this.field2 = " ";
  }
  public B(String value) {
    this.field2 = value;
    this.field1 = 0;
  }
}

class TestAnonymousInnerClasses { // ok, class has an anonymous inner class.
  public static final TestAnonymousInnerClasses ONE
          = new TestAnonymousInnerClasses() {};

  private TestAnonymousInnerClasses() {
  }
}

class Class1 {

  private class Class2 { // violation, 'Class Class2 should be declared as final'
  }

  public class Class3 {
  }

}
// xdoc section -- end
