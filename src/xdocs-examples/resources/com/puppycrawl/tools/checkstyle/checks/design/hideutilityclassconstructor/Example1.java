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

    public static void funn() {
    }
}

class Foo { // OK

    private Foo() {

    }

    public  int  n;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}

class Bar { // OK

    protected Bar() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }
}

class UtilityClass { // violation, class only has a static field

   private static float f;
}
// xdoc section -- end
