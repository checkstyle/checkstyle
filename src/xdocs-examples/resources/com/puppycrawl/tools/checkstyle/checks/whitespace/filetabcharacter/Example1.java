/*xml
<module name="Checker">
  <module name="FileTabCharacter"/>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.filetabcharacter;

class Example1 {
  // xdoc section -- start
	int a; // violation 'File contains tab characters'

	public void foo (int arg) { // OK, only first occurrence in file reported
    a = arg; // OK, indented using spaces
  }
  // xdoc section -- end
}
