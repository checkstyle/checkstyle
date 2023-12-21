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
  int counter = 1;
  int count = 0;
  int num = 12;

  public boolean Example4() {
    if (obj.equals(1)) return true;
    // ok, because IF is not a target of validation
    if (true) {
      obj.equals(2);
    } else
      // ok, because ELSE is not a target of validation
      return false;
    for (int i = 0; i < 5; i++) {
      ++count;}
    do
      ++count;
    while (false);
    for (int j = 0; j < 10; j++);
    // ok, because FOR is not a target of validation
    for(int i = 0; i < 10; value.charAt(9));
    // ok, because FOR is not a target of validation
    while (counter < 10)
      // ok, because WHILE is not a target of validation
      ++count;
    while (value.charAt(3) < 5);
    // ok, because WHILE is not a target of validation
    switch (num) {
      case 1: counter++; break;
      case 6: counter += 10; break;
      default: counter = 100; break;
    }
    return true;
  }
}

// xdoc section -- end
