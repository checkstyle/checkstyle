/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class UseCase1 {
      int a;               // violation 'level 6, expected level should be 4'

    void method() {
        int b = 0;
          int c = 1;       // violation 'level 10, expected level should be 8'
    }
}
// xdoc section -- end
