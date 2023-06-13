/*
FileTabCharacter
fileExtensions = java, xml


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.filetabcharacter;

class Example3 {
    // xdoc section -- start
	int a; // violation 'File contains tab characters'

	public void foo (int arg) { // OK, only first occurrence in file reported
    a = arg; // OK, indented using spaces
  }
  // xdoc section -- end
}
