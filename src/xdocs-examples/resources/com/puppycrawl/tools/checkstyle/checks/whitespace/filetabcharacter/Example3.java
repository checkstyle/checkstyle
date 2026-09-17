/*xml
<module name="Checker">
  <module name="FileTabCharacter">
    <property name="fileExtensions" value="xml"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.filetabcharacter;

// xdoc section - start
class Example3 {
	int a; // ok, no check performed on java file extension

	public void foo (int arg) { // ok, java is not specified in check config
    a = arg; // ok, indented using spaces
  }
}
// xdoc section - end
