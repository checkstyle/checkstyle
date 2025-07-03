/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RecordComponentNumber">
      <property name="max" value="5"/>
    </module>
  </module>
</module>
*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

// xdoc section -- start
class Example2{
  public record MyRecord1(int x, int y, String str) {}

  public record MyRecord2(int x, int y, double d, // violation, 6 components
                    String str, char c, float f) {}

  record MyRecord3(int x, int y, int z, double d, // violation, 9 components
                    String str1, String str2, char c, float f, String location) {}

  private record MyRecord4(int x, int y,
                           String str, double d) {}
}
// xdoc section -- end
