/*
FileTabCharacter
eachLine = (default)false
fileExtensions = java, xml


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.filetabcharacter;

class Example3 {
	int a; // violation 'File contains tab characters'

	public void foo (int arg) { // OK, only first occurrence in file reported
    a = arg; // OK, indented using spaces
  }
}
