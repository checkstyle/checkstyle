/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineSeparator">
      <property name="allowMultipleEmptyLinesInsideClassMembers"
                value="false"/>
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
class Example7 { // violation above ''import' should be separated from previous line'
  // violation above ''CLASS_DEF' should be separated from previous line'
  int var1 = 1;
  int var2 = 2; // violation ''VARIABLE_DEF' should be separated from previous line'


  int var3 = 3;


  void method1() {}
  void method2() { // violation ''METHOD_DEF' should be separated from previous line'
    int var4 = 4; // violation 'There is more than 1 empty line after this line'


    int var5 = 5;
  }
}
// xdoc section -- end
