/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessarySemicolonInEnumeration"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicoloninenumeration;

// xdoc section -- start
enum One {
    A,B; // violation, 'Unnecessary semicolon'
}

enum Two {
    A,B,; // violation, 'Unnecessary semicolon'
}

enum Three {
    A,B(); // violation, 'Unnecessary semicolon'
}

enum Four {
    A,B{}; // violation, 'Unnecessary semicolon'
}

enum Five {
    A,
    B
    ; // violation, 'Unnecessary semicolon'
}

enum Six {
    A,
    B,
    ; // ok, enum body contains constructor.
  Six(){}
}
// xdoc section -- end
