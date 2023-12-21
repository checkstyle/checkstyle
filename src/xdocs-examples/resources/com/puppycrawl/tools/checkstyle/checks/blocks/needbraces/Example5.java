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
  Helper obj = new Helper();
  Helper value = new Helper();
  int count = 0;
  int counter = 1;
  int num = 3;
  public boolean Example5() {
    if (obj.isValid(3)) return true;
    if (true) {
      obj.isValid(4);
    } else
        return false;
    for (int i = 0; i < 5; i++) {
      ++count;
    }
    do
        ++count;
    while (false);
    for (int j = 0; j < 10; j++);
    for(int i = 0; i < 10; value.incrementValue(2));
    while (counter < 10)           // violation
        ++count;
    while (value.incrementValue(2) < 5);
    switch (num) {
      case 1: counter++; break;
    }
    return true;
  }
}
// xdoc section -- end
