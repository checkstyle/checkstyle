/*
FileTabCharacter
eachLine = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.filetabcharacter;

class Example2 {
  // xdoc section -- start
	int a; // violation 'contains a tab character'

	public void foo (int arg) { // violation 'contains a tab character'
    a = arg; // OK, indented using spaces
  }
  // xdoc section -- end
}
