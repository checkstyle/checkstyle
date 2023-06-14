/*
FileTabCharacter
eachLine = true
fileExtensions = (default)


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.filetabcharacter;

class Example2 {
	int a; // violation 'contains a tab character'

	public void foo (int arg) { // violation 'contains a tab character'
    a = arg; // OK, indented using spaces
  }
}
