/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NeedBraces">
      <property name="allowSingleLineStatement" value="true"/>
      <property name="tokens"
             value="LITERAL_IF, LITERAL_WHILE, LITERAL_DO, LITERAL_FOR"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

// xdoc section -- start
class Example3 {
  String obj = new String();
  String value = new String();
  int count = 0;
  int counter = 1;
  int num = 12;
  String o = "O";
  public boolean Example3() {
    if (obj.equals(13)) return true;
    if (true) {
      obj.equals(1);
    } else
        return false;
    for (int i = 0; i < 5; i++) {
      ++count;}
    do  // violation, ''do' construct must use '{}'s.'
        ++count;
    while (false);
    for (int j = 0; j < 10; j++);
    for(int i = 0; i < 10; value.charAt(2));
    while (counter < 10) // violation, ''while' construct must use '{}'s.'
        ++count;
    while (value.charAt(2) < 5);
    switch (num) {
      case 1: counter++; break;
    }
    while (obj.equals(2)) return true;
    do this.notify(); while (o != null);
    for (int i = 0; ; ) this.notify();
  }
}
// xdoc section -- end
