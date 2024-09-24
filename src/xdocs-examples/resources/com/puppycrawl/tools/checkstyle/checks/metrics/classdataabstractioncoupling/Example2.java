/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassDataAbstractionCoupling"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.Example7;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.Example4;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.Example5;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.Example6;

import java.math.BigInteger;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

// xdoc section -- start
// violation below, "Class Data Abstraction Coupling is 8 (max allowed is 7)."
public class Example2 {
  Set set = new HashSet(); // Ignored by default
  Map map = new HashMap(); // Ignored by default

  AtomicInteger atomicInteger = new AtomicInteger(); // Counted 1
  BigInteger bigInteger = new BigInteger("0");
  Example1 example1 = new Example1();
  Example3 example3 = new Example3();
  Example4 example4 = new Example4();
  Example5 example5 = new Example5();
  Example6 example6 = new Example6();
  Example7 example7 = new Example7(); // Counted 8
}
// xdoc section -- end
