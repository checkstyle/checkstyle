/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DuplicateSwitchBranch"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.duplicateswitchbranch;

// xdoc section - start
public class Example1 {
  int getNumber(String value) {
    switch (value) {
      case "A":
        return 1;
      // violation 2 lines below """Duplicate switch branch implementation identical
      //  to the branch at line 15."""
      case "B":
        return 1;
      case "C":
        return 2;
      default:
        return 0;
    }
  }

  int getNumberMerged(String value) {
    // ok, branches with the same body already share one set of labels
    switch (value) {
      case "A":
      case "B":
        return 1;
      default:
        return 0;
    }
  }
}
// xdoc section - end
