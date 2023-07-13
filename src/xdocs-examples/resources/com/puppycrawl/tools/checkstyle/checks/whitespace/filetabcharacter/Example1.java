/*xml
<module name="Checker">
  <module name="FileTabCharacter"/>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.filetabcharacter;

// xdoc section -- start
class Example1 {
	int a; // violation 'File contains tab characters'

	public void foo (int arg) { // OK, only first occurrence in file reported
    a = arg; // OK, indented using spaces
  }
}
// xdoc section -- end
