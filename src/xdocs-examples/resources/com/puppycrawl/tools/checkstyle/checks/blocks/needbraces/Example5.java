/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NeedBraces">
      <property name="allowEmptyLoopBody" value="true"/>
      <property name="tokens" value="LITERAL_WHILE, LITERAL_FOR"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

// xdoc section -- start
class Example5 {
  String obj = new String();
  String value = new String();
  int counter = 1;
  int count = 0;
  int num = 12;
  String o = "O";
  public boolean Example5() {
    if (obj.equals(num)) return true;
    // ok above, because IF is not a target of validation
    if (true) {
      count = 2;
    } else
        // ok above, because ELSE is not a target of validation
        return false;
    for (int i = 0; i < 5; i++) {
      ++count;}
    do
        ++count;
    while (false);
    for (int j = 0; j < 10; j++);
    // ok above, because FOR is not a target of validation
    for(int i = 0; i < 10; value.charAt(12));
    // ok above, because FOR is not a target of validation
    while (counter < 10)
        // violation above, ''while' construct must use '{}'s.'
        ++count;
    while (value.charAt(12) < 5);
    // ok above, because WHILE is not a target of validation
    switch (num) {
      case 1: counter++; break;
    }
    return true;
  }
}



// xdoc section -- end
