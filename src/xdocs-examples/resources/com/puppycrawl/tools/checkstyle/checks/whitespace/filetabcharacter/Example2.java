/*xml
<module name="Checker">
  <module name="FileTabCharacter">
    <property name="eachLine" value="true"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.filetabcharacter;

// xdoc section -- start
class Example2 {
	int a; // violation 'contains a tab character'

	public void foo (int arg) { // violation 'contains a tab character'
    a = arg; // OK, indented using spaces
  }
}
// xdoc section -- end
