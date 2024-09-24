/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassDataAbstractionCoupling">
      <property name="excludedClasses" value="HashMap, HashSet, Example1"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper;

import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.Example2;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.Example3;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.Example7;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.Example8;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

// xdoc section -- start
// violation below, "Class Data Abstraction Coupling is 8 (max allowed is 7)."
public class Example6 {
  Set set = new HashSet(); // Ignored by default
  Map map = new HashMap(); // Ignored by default

  AtomicInteger atomicInteger = new AtomicInteger(); // Counted 1
  BigInteger bigInteger = new BigInteger("0");
  Example2 example2 = new Example2();
  Example3 example3 = new Example3();
  Example4 example4 = new Example4();
  Example5 example5 = new Example5();
  Example7 example7 = new Example7();
  Example8 example8 = new Example8(); // Counted 8
}
// xdoc section -- end
