/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantModifier">
        <property name="jdkVersion" value="11"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

// xdoc section -- start
public class Example3 {

  void test() {
    // violation below, 'Redundant 'final' modifier'
    try (final var a = lock()) {

    } catch (Exception e) {

    }
  }

  // violation below, 'Redundant 'abstract' modifier'
  abstract interface I {
    public abstract void m();
    // 2 violations above:
    //    'Redundant 'public' modifier'
    //    'Redundant 'abstract' modifier'
    public int x = 0; // violation, 'Redundant 'public' modifier'
  }

  static enum E { // violation, 'Redundant 'static' modifier'
        A, B, C
  }

  // ok below, 'strictfp' is not redundant before JDK 17
  public strictfp class Test { }

  AutoCloseable lock() {
    return null;
  }

}
// xdoc section -- end
