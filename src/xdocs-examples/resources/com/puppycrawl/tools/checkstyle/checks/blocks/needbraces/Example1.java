/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NeedBraces"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

// xdoc section -- start
class Example1 {
  Helper obj = new Helper();
  public boolean Example1() {
    int num = 12;
    if (obj.isValid(num)) return true; // violation
    int count = 0;
    if (true) { // OK
      count = 2;
    } else // violation
        return false;
    for (int i = 0; i < 5; i++) { // OK
      ++count;
    }

    do // violation
        ++count;
    while (false);

    for (int j = 0; j < 10; j++); // violation
    Helper value = new Helper();
    for(int i = 0; i < 10; value.incrementValue(12)); // violation
    int counter = 1;
    while (counter < 10) // violation
        ++count;

    while (value.incrementValue(12) < 5); // violation

    switch (num) {
      case 1: counter++; break; // OK
    }
    return true;
  }
}

class Helper {
  public boolean isValid(int obj) {
    if (obj == 12) {
      return true;
    }
    return false;
  }
  public int incrementValue(int num) {
    return num++;
  }
}
// xdoc section -- end
