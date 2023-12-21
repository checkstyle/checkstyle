/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HideUtilityClassConstructor"/>
  </module>
</module>
*/

// xdoc section -- start
class Test { // violation, class only has a static method and a constructor

    public Test() {
    }

    public static void fun() {
    }
}

class Foo {

    static int n;

    private Foo() {
    }
}

class Bar {

    protected Bar() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }
}

class UtilityClass { // violation, class only has a static field

    static float f;
}
// xdoc section -- end
