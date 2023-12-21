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
  Helper obj = new Helper();
  Helper value = new Helper();
  int counter = 1;
  int count = 0;
  public boolean Example2() {
    if (obj.isValid(12)) return true; // violation
    if (true) {
      obj.isValid(11);
    } else // violation
        return false;

    for (int i = 0; i < 5; i++) {
      ++count;
    }
    do
        ++count;
    while (false);
    for (int j = 0; j < 10; j++);
    for(int i = 0; i < 10; value.incrementValue(1));
    while (counter < 10)
        ++count;
    while (value.incrementValue(1) < 5);
    int num = 12;
    switch (num) {
      case 1: counter++; break;
    }
    return true;
  }
}
// xdoc section -- end
