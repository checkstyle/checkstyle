/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DefaultComesLast"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.defaultcomeslast;

// xdoc section -- start
public class Example1 {

  public void method() {
    int i = 2, x;
    switch (i) {
      case 1:
        break;
      case 2:
        break;
      default:
        break;
    }

    switch (i) {
      case 1:
        break;
      case 2:
        break;
    }

    switch (i) {
      case 1:
        break;
      default: // violation, 'Default should be last label in the switch'
        break;
      case 2:
        break;
    }

    switch (i) {
      case 1:
        break;
      default:
      case 2:
        break;
    }

    switch (i) {
      case 1: x = 9;
      default: x = 10; // violation, 'Default should be last label in the switch'
      case 2: x = 32;
    }
  }
}
// xdoc section -- end
