/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantModifier">
      <property name="tokens" value="METHOD_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

// xdoc section -- start
public class Example2 {

  void test() {
    try (final var a = lock()) {

    } catch (Exception e) {

    }
  }

  abstract interface I {
    public abstract void m();
    // 2 violations above:
    //    'Redundant 'public' modifier'
    //    'Redundant 'abstract' modifier'
    public int x = 0;
  }

  static enum E {
        A, B, C
  }

  public strictfp class Test { }

  AutoCloseable lock() {
    return null;
  }
}
// xdoc section -- end
