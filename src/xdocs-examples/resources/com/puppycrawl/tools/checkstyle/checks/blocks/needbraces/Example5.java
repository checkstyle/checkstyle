/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NeedBraces">
      <property name="allowSameLineTrailingSubstatement" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

// xdoc section - start
class Example5 {
  String obj = new String();
  String value = new String();
  int counter = 1;
  int count = 0;
  int num = 12;
  String o = "O";
  public boolean test() {
    if (obj.equals(num)) return true;
    // ok above, because single-line IF is allowed (same-line trailing substatement).
    if (true) {
      count = 2;
    } else
        // violation above ''else' construct must use '{}'s.'
        return false;
    for (int i = 0; i < 5; i++) {
      ++count;}
    do // violation ''do' construct must use '{}'s.'
        ++count;
    while (false);
    for (int j = 0; j < 10; j++);
    // ok above, because single-line FOR is allowed.
    for(int i = 0; i < 10; value.charAt(12));
    // ok above, because single-line FOR is allowed.
    while (counter < 10)
        // violation above ''while' construct must use '{}'s.'
        ++count;
    while (value.charAt(12) < 5);
    // ok above, because single-line WHILE is allowed.
    switch (num) {
      case 1: counter++; break;
      // ok above, because break in case blocks is not counted to allow compact view
    }
    return true;
  }
}
// xdoc section - end
