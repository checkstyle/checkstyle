/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="StringLiteralEquality"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.stringliteralequality;

// xdoc section -- start
class Example1 {
  String getName(){
    return "Y";
  }
  void InvalidExample(){
    String status = "pending";
    // violation below, 'Literal Strings should be compared using equals(), not '==''
    if (status == "done") {}
    // violation below, 'Literal Strings should be compared using equals(), not '!=''
    while (status != "done") {}
    // violation below, 'Literal Strings should be compared using equals(), not '==''
    boolean flag = (status == "done");
    boolean flag1 = (status.equals("done"));
    String name = "X";
    if (name == getName()) {}
    // ok, limitation that check cannot tell runtime type returned from method call
  }
}
// xdoc section -- end
