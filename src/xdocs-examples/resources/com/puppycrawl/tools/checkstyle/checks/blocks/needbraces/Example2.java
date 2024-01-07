/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NeedBraces">
      <property name="tokens" value="LITERAL_IF, LITERAL_ELSE"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

// xdoc section -- start
class Example2 {
  String obj = new String();
  String value = new String();
  int counter = 1;
  int count = 0;
  int num = 12;
  String o = "O";
  public boolean Example2() {
    if (obj.equals(num)) return true;
    // violation above, ''if' construct must use '{}'s.'
    if (true) {
      count = 2;
    } else
        // violation above, ''else' construct must use '{}'s.'
        return false;
    for (int i = 0; i < 5; i++) {
      ++count;}
    do // ok, because DO is not a target of validation
        ++count;
    while (false);
    for (int j = 0; j < 10; j++);
    // ok above, because FOR is not a target of validation
    for(int i = 0; i < 10; value.charAt(12));
    // ok above, because FOR is not a target of validation
    while (counter < 10)
        // ok above, because WHILE is not a target of validation
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
