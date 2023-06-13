/*
EmptyForIteratorPad


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptyforiteratorpad;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Example1 {
  Map<String, String> map = new HashMap<>();
  void example() {
    // xdoc section -- start
    for (Iterator it = map.entrySet().iterator();  it.hasNext(););
    for (Iterator it = map.entrySet().iterator();  it.hasNext(); );
    // violation above '';' is followed by whitespace'

    for (Iterator foo = map.entrySet().iterator();
         foo.hasNext();
         );
    // xdoc section -- end
  }
}
