/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HideUtilityClassConstructor"/>
  </module>
</module>
*/

// xdoc section -- start
class Test { // violation, 'Utility classes should not have a public or default constructor'

  public Test() {
  }

  public static void fun() {
  }
}

class Foo {

  private Foo() {
  }

  static int n;
}

class Bar {

  protected Bar() {
    // prevents calls from subclass
    throw new UnsupportedOperationException();
  }
}

class UtilityClass { // violation, 'Utility classes should not have a public or default constructor'

  static float f;
}
// xdoc section -- end
