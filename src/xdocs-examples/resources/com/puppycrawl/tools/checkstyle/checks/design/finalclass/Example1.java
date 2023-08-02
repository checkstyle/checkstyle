/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalClass"/>
  </module>
</module>
*/

// xdoc section -- start
final class MyClassA { // OK
  private MyClassA() { }
}

class MyClassB { // violation 'Class MyClassB should be declared as final.'
  private MyClassB() { }
}

class MyClassC { // OK, since it has a public constructor
  int field1;
  String field2;
  private MyClassC(int value) {
    this.field1 = value;
    this.field2 = " ";
  }
  public MyClassC(String value) {
    this.field2 = value;
    this.field1 = 0;
  }
}

class TestAnonymousInnerClasses { // OK, 'class has an anonymous inner class.'
    public static final TestAnonymousInnerClasses ONE = new TestAnonymousInnerClasses() {

    };

    private TestAnonymousInnerClasses() {
    }
}

class Class1 {

    private class Class2 { // violation 'Class Class2 should be declared as final'
    }

    public class Class3 { // ok
    }

}
// xdoc section -- end
