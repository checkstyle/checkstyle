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
  String obj = new String();
  public boolean Example1() {
    int num = 12;
    if (obj.equals(num)) return true;
    // violation above, ''if' construct must use '{}'s.'
    // single-line statements not allowed without braces.
    int count = 0;
    if (true) {
      count = 2;
    } else
        // violation above, ''else' construct must use '{}'s.'
        // single-line statements not allowed without braces.
        return false;
    for (int i = 0; i < 5; i++) {
      ++count;
    }
    do  // single-line statements not allowed without braces.
        // violation above, ''do' construct must use '{}'s.'
        ++count;
    while (false);

    for (int j = 0; j < 10; j++); // empty loop body not allowed.
    // violation above, ''for' construct must use '{}'s.'
    String value = new String();
    for(int i = 0; i < 10; value.equals(12)); // empty loop body not allowed.
    // violation above, ''for' construct must use '{}'s.'
    int counter = 1;
    while (counter < 10) // single-line statements not allowed without braces.
        // violation above, ''while' construct must use '{}'s.'
        ++count;

    while (value.charAt(12) < 5);//single-line statements not allowed without braces.
    // violation above, ''while' construct must use '{}'s.'

    switch (num) {
      case 1: counter++; break;
    }
    return true;
  }
}
// xdoc section -- end
