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
  public boolean Example2() {
    if (obj.equals(12)) return true;
    // violation above, ''if' construct must use '{}'s.'
    if (true) {
      obj.equals(11);
    } else
        // violation above, ''else' construct must use '{}'s.'
        return false;
    for (int i = 0; i < 5; i++) {
      ++count;
    }
    do
        ++count;
    while (false);
    for (int j = 0; j < 10; j++);

    for(int i = 0; i < 10; value.charAt(1));

    while (counter < 10)
        ++count;
    while (value.charAt(1) < 5);
    int num = 12;
    switch (num) {
      case 1: counter++; break;
    }
    return true;
  }
}
// xdoc section -- end
