/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NeedBraces">
      <property name="tokens" value="LITERAL_CASE, LITERAL_DEFAULT"/>
      <property name="allowSingleLineStatement" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

// xdoc section -- start
class Example4 {
  String obj = new String();
  String value = new String();
  int count = 0;
  int counter = 1;
  int num = 0;

  public boolean Example4() {
    if (obj.equals(1)) return true;
    if (true) {
      obj.equals(2);
    } else
      return false;
    for (int i = 0; i < 5; i++) {
      ++count;
    }
    do
      ++count;
    while (false);
    for (int j = 0; j < 10; j++);
    for(int i = 0; i < 10; value.charAt(9));
    while (counter < 10)
      ++count;
    while (value.charAt(3) < 5);
    switch (num) {
      case 1: counter++; break;
      case 6: counter += 10; break;
      default: counter = 100; break;
    }
    return true;
  }
}
// xdoc section -- end
