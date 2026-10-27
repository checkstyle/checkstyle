/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OuterTypeNumber">
      <property name="max" value="0"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
    System.out.println("Hello!");
}

class A {
}

interface B {
}

enum C {
    ONE
}

record D(int x) {
}

@interface E {
}
