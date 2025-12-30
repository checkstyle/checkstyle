/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineSeparator">
      <property name="allowMultipleEmptyLines" value="false"/>
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
class Example6 { // violation above ''import' should be separated from previous line'
  // violation above ''CLASS_DEF' should be separated from previous line'
  int var1 = 1;
  int var2 = 2; // violation ''VARIABLE_DEF' should be separated from previous line'


  int var3 = 3; // violation ''VARIABLE_DEF' has more than 1 empty lines before'


  void method1() {} // violation ''METHOD_DEF' has more than 1 empty lines before'
  void method2() { // violation ''METHOD_DEF' should be separated from previous line'
    int var4 = 4;


    int var5 = 5;
  }
}
// xdoc section -- end
