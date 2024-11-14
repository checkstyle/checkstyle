/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RecordComponentNumber">
      <property name="max" value="5"/>
    </module>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

// xdoc section -- start
class Example2{
  public record MyRecord1(int x, int y, int z) {}// ok, 2 components

  public record MyRecord2(int x, int y, double d, // violation, 6 components
                    String str, char c, float f) {}
}
// xdoc section -- end
