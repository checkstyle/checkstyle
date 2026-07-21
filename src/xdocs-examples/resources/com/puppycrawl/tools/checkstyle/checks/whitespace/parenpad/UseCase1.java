/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParenPad"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

import java.util.Iterator;
import java.io.Closeable;
// xdoc section -- start
class UseCase1 {
  void m(int i, int j, Iterator xs, Closeable resource) throws Exception {
    for ( ; i < j; i++, j--) {}               // ok, no check after left '('
    for (Iterator it = xs; it.hasNext(); ) {} // ok, no check before right ')'
    try (Closeable r = resource; ) {}         // ok, no check before right ')
  }
}
// xdoc section -- end
