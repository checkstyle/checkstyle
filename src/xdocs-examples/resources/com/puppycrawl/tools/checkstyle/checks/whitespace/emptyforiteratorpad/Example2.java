/*
EmptyForIteratorPad
option = space


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptyforiteratorpad;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Example2 {
  Map<String, String> map = new HashMap<>();
  void example() {
    // xdoc section -- start
    for (Iterator it = map.entrySet().iterator();  it.hasNext(););
    // violation above '';' is not followed by whitespace.'
    for (Iterator it = map.entrySet().iterator();  it.hasNext(); );

    for (Iterator foo = map.entrySet().iterator();
         foo.hasNext();
         );
    // xdoc section -- end
  }
}
