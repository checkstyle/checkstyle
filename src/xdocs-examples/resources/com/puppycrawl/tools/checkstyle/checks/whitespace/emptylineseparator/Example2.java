/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineSeparator">
      <property name="tokens" value="PACKAGE_DEF"/>
    </module>
  </module>
</module>


*/
// xdoc section -- start
///////////////////////////////////////////////////
//HEADER
///////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;
import java.io.Serializable; // violation above ''package' should be separated from previous line'
class Example2 { // violation above ''import' should be separated from previous line'

  int var1 = 1;
  int var2 = 2;


  int var3 = 3;


  void method1() {}
  void method2() {
    int var4 = 4;


    int var5 = 5;
  }
}
// xdoc section -- end
